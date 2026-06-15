package com.springmart.product.service.impl;

import com.springmart.product.dto.request.ProductRequest;
import com.springmart.product.dto.request.StatusUpdateRequest;
import com.springmart.product.dto.request.StockUpdateRequest;
import com.springmart.product.dto.response.PagedResponse;
import com.springmart.product.dto.response.ProductResponse;
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

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final BrandRepository brandRepository;
    private final ProductMapper productMapper;

    @Override
    public ProductResponse createProduct(ProductRequest request) {
        if (productRepository.existsBySku(request.getSku())) {
            throw new DuplicateResourceException("Product already exists with SKU: " + request.getSku());
        }
        
        validateCategoryAndBrand(request.getCategoryId(), request.getBrandId());

        Product product = productMapper.toEntity(request);
        // Default values for new product
        product.setStatus(ProductStatus.DRAFT);
        product.setAverageRating(0.0);
        product.setReviewCount(0);
        product.setSlug(generateSlug(request.getProductName()));

        Product savedProduct = productRepository.save(product);
        return productMapper.toResponse(savedProduct);
    }

    @Override
    public ProductResponse updateProduct(String id, ProductRequest request) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found with id: " + id));

        if (!product.getSku().equals(request.getSku()) && productRepository.existsBySku(request.getSku())) {
            throw new DuplicateResourceException("Product already exists with SKU: " + request.getSku());
        }

        validateCategoryAndBrand(request.getCategoryId(), request.getBrandId());

        productMapper.updateEntityFromRequest(request, product);
        product.setSlug(generateSlug(request.getProductName()));
        
        Product updatedProduct = productRepository.save(product);
        return productMapper.toResponse(updatedProduct);
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
        return productMapper.toResponse(product);
    }

    @Override
    public ProductResponse getProductBySku(String sku) {
        Product product = productRepository.findBySku(sku)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found with SKU: " + sku));
        return productMapper.toResponse(product);
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
    public PagedResponse<ProductResponse> searchProducts(String keyword, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
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
        return productMapper.toResponse(updatedProduct);
    }

    @Override
    public ProductResponse updateStatus(String id, StatusUpdateRequest request) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found with id: " + id));
        product.setStatus(request.getStatus());
        Product updatedProduct = productRepository.save(product);
        return productMapper.toResponse(updatedProduct);
    }

    private void validateCategoryAndBrand(String categoryId, String brandId) {
        if (!categoryRepository.existsById(categoryId)) {
            throw new ResourceNotFoundException("Category not found with id: " + categoryId);
        }
        if (!brandRepository.existsById(brandId)) {
            throw new ResourceNotFoundException("Brand not found with id: " + brandId);
        }
    }

    private String generateSlug(String productName) {
        if (productName == null) return "";
        return productName.toLowerCase().replaceAll("[^a-z0-9\\s-]", "").replaceAll("\\s+", "-");
    }

    private PagedResponse<ProductResponse> mapToPagedResponse(Page<Product> page) {
        List<ProductResponse> content = productMapper.toResponseList(page.getContent());
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
