package com.springmart.product.service.impl;

import com.springmart.product.dto.request.ProductRequest;
import com.springmart.product.dto.request.StatusUpdateRequest;
import com.springmart.product.dto.request.StockUpdateRequest;
import com.springmart.product.dto.response.PagedResponse;
import com.springmart.product.dto.response.ProductResponse;
import com.springmart.product.entity.Brand;
import com.springmart.product.entity.Category;
import com.springmart.product.entity.Product;
import com.springmart.product.enums.ProductStatus;
import com.springmart.product.exception.DuplicateResourceException;
import com.springmart.product.exception.ResourceNotFoundException;
import com.springmart.product.mapper.ProductMapper;
import com.springmart.product.repository.BrandRepository;
import com.springmart.product.repository.CategoryRepository;
import com.springmart.product.repository.ProductRepository;
import com.springmart.product.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@SuppressWarnings("null")
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final BrandRepository brandRepository;
    private final ProductMapper productMapper;

    @Override
    public ProductResponse createProduct(ProductRequest request) {
        validateCategory(request.getCategoryId());

        String sku = StringUtils.hasText(request.getSku()) ? request.getSku() : generateSku();
        if (productRepository.existsBySku(sku)) {
            throw new DuplicateResourceException("Product already exists with SKU: " + sku);
        }

        Brand brand = resolveBrand(request.getBrandName());
        Product product = productMapper.toEntity(request);
        product.setSku(sku);
        product.setBrandId(brand.getId());
        product.setBrandName(brand.getName());
        product.setStatus(ProductStatus.APPROVED);
        product.setAverageRating(0.0);
        product.setReviewCount(0);
        product.setSlug(generateSlug(request.getProductName()));

        Product savedProduct = productRepository.save(product);
        return enrichResponse(savedProduct);
    }

    @Override
    public ProductResponse updateProduct(String id, ProductRequest request) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found with id: " + id));

        validateCategory(request.getCategoryId());

        if (StringUtils.hasText(request.getSku())
                && !product.getSku().equals(request.getSku())
                && productRepository.existsBySku(request.getSku())) {
            throw new DuplicateResourceException("Product already exists with SKU: " + request.getSku());
        }

        Brand brand = resolveBrand(request.getBrandName());
        productMapper.updateEntityFromRequest(request, product);
        if (StringUtils.hasText(request.getSku())) {
            product.setSku(request.getSku());
        }
        product.setBrandId(brand.getId());
        product.setBrandName(brand.getName());
        product.setSlug(generateSlug(request.getProductName()));

        Product updatedProduct = productRepository.save(product);
        return enrichResponse(updatedProduct);
    }

    @Override
    public void deleteProduct(String id) {
        if (!productRepository.existsById(id)) {
            throw new ResourceNotFoundException("Product not found with id: " + id);
        }
        productRepository.deleteById(id);
    }

    @Override
    public ProductResponse getProductById(String id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found with id: " + id));
        return enrichResponse(product);
    }

    @Override
    public ProductResponse getProductBySku(String sku) {
        Product product = productRepository.findBySku(sku)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found with SKU: " + sku));
        return enrichResponse(product);
    }

    @Override
    public PagedResponse<ProductResponse> getAllProducts(int page, int size, String sortBy, String sortDir) {
        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();
        Pageable pageable = PageRequest.of(page, size, sort);
        Page<Product> products = productRepository.findAll(pageable);
        return mapToPagedResponse(products);
    }

    @Override
    public PagedResponse<ProductResponse> getProductsByCategory(String categoryId, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Product> products = productRepository.findByCategoryId(categoryId, pageable);
        return mapToPagedResponse(products);
    }

    @Override
    public PagedResponse<ProductResponse> getProductsByBrand(String brandId, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Product> products = productRepository.findByBrandId(brandId, pageable);
        return mapToPagedResponse(products);
    }

    @Override
    public PagedResponse<ProductResponse> getProductsByMerchantUuid(String merchantUuid, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Product> products = productRepository.findByMerchantUuid(merchantUuid, pageable);
        return mapToPagedResponse(products);
    }

    @Override
    public List<ProductResponse> getAllProductsByMerchantUuid(String merchantUuid) {
        List<Product> products = productRepository.findByMerchantUuid(merchantUuid);
        return products.stream().map(this::enrichResponse).collect(Collectors.toList());
    }

    @Override
    public PagedResponse<ProductResponse> searchProducts(String keyword, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        List<Category> matchingCategories = categoryRepository.findByNameContainingIgnoreCase(keyword);
        if (!matchingCategories.isEmpty()) {
            List<String> categoryIds = matchingCategories.stream().map(Category::getId).collect(Collectors.toList());
            Page<Product> categoryProducts = productRepository.findByCategoryIdIn(categoryIds, pageable);
            if (categoryProducts.hasContent()) {
                return mapToPagedResponse(categoryProducts);
            }
        }

        Page<Product> products = productRepository.searchByKeyword(keyword, pageable);
        return mapToPagedResponse(products);
    }

    @Override
    public PagedResponse<ProductResponse> searchByPriceRange(BigDecimal min, BigDecimal max, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Product> products = productRepository.findByPriceBetween(min, max, pageable);
        return mapToPagedResponse(products);
    }

    @Override
    public ProductResponse updateStock(String id, StockUpdateRequest request) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found with id: " + id));
        product.setQuantity(request.getQuantity());
        Product updatedProduct = productRepository.save(product);
        return enrichResponse(updatedProduct);
    }

    @Override
    public ProductResponse updateStatus(String id, StatusUpdateRequest request) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found with id: " + id));
        product.setStatus(request.getStatus());
        Product updatedProduct = productRepository.save(product);
        return enrichResponse(updatedProduct);
    }

    private void validateCategory(String categoryId) {
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException("Category not found with id: " + categoryId));
        if (!category.isActive()) {
            throw new ResourceNotFoundException("Category is not available: " + category.getName());
        }
    }

    private Brand resolveBrand(String brandName) {
        if (!StringUtils.hasText(brandName)) {
            throw new ResourceNotFoundException("Brand name is required");
        }

        String normalizedName = brandName.trim();
        return brandRepository.findByName(normalizedName).orElseGet(() ->
                brandRepository.save(Brand.builder()
                        .name(normalizedName)
                        .description("Merchant brand")
                        .active(true)
                        .build())
        );
    }

    private String generateSku() {
        return "SM-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();
    }

    private String generateSlug(String productName) {
        if (productName == null) {
            return "";
        }
        return productName.toLowerCase().replaceAll("[^a-z0-9\\s-]", "").replaceAll("\\s+", "-");
    }

    private ProductResponse enrichResponse(Product product) {
        ProductResponse response = productMapper.toResponse(product);
        categoryRepository.findById(product.getCategoryId())
                .ifPresent(category -> response.setCategoryName(category.getName()));
        if (StringUtils.hasText(product.getBrandName())) {
            response.setBrandName(product.getBrandName());
        } else {
            brandRepository.findById(product.getBrandId())
                    .ifPresent(brand -> response.setBrandName(brand.getName()));
        }
        return response;
    }

    private PagedResponse<ProductResponse> mapToPagedResponse(Page<Product> page) {
        List<ProductResponse> content = page.getContent().stream()
                .map(this::enrichResponse)
                .collect(Collectors.toList());
        return PagedResponse.<ProductResponse>builder()
                .content(content)
                .pageNo(page.getNumber())
                .pageSize(page.getSize())
                .totalElements(page.getTotalElements())
                .totalPages(page.getTotalPages())
                .last(page.isLast())
                .build();
    }
}
