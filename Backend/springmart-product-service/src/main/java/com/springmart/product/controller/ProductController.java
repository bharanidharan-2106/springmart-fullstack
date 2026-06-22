package com.springmart.product.controller;

import com.springmart.product.dto.request.ProductRequest;
import com.springmart.product.dto.request.StatusUpdateRequest;
import com.springmart.product.dto.request.StockUpdateRequest;
import com.springmart.product.dto.response.ApiResponse;
import com.springmart.product.dto.response.PagedResponse;
import com.springmart.product.dto.response.ProductResponse;
import com.springmart.product.service.ProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasAnyRole('MERCHANT', 'ADMIN')")
    public ApiResponse<ProductResponse> createProduct(@Valid @RequestBody ProductRequest request) {
        ProductResponse response = productService.createProduct(request);
        return ApiResponse.success("Product created successfully", response);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('MERCHANT', 'ADMIN')")
    public ApiResponse<ProductResponse> updateProduct(@PathVariable String id, @Valid @RequestBody ProductRequest request) {
        ProductResponse response = productService.updateProduct(id, request);
        return ApiResponse.success("Product updated successfully", response);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ApiResponse<Void> deleteProduct(@PathVariable String id) {
        productService.deleteProduct(id);
        return ApiResponse.success("Product deleted successfully", null);
    }

    @GetMapping("/{id}")
    public ApiResponse<ProductResponse> getProductById(@PathVariable String id) {
        ProductResponse response = productService.getProductById(id);
        return ApiResponse.success("Product fetched successfully", response);
    }

    @GetMapping("/sku/{sku}")
    public ApiResponse<ProductResponse> getProductBySku(@PathVariable String sku) {
        ProductResponse response = productService.getProductBySku(sku);
        return ApiResponse.success("Product fetched successfully", response);
    }

    @GetMapping
    public ApiResponse<PagedResponse<ProductResponse>> getAllProducts(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "createdAt") String sortBy,
            @RequestParam(defaultValue = "DESC") String sortDir) {
        PagedResponse<ProductResponse> response = productService.getAllProducts(page, size, sortBy, sortDir);
        return ApiResponse.success("Products fetched successfully", response);
    }

    @GetMapping("/category/{categoryId}")
    public ApiResponse<PagedResponse<ProductResponse>> getProductsByCategory(
            @PathVariable String categoryId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        PagedResponse<ProductResponse> response = productService.getProductsByCategory(categoryId, page, size);
        return ApiResponse.success("Products fetched successfully", response);
    }

    @GetMapping("/brand/{brandId}")
    public ApiResponse<PagedResponse<ProductResponse>> getProductsByBrand(
            @PathVariable String brandId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        PagedResponse<ProductResponse> response = productService.getProductsByBrand(brandId, page, size);
        return ApiResponse.success("Products fetched successfully", response);
    }

    @GetMapping("/merchant/{merchantUuid}")
    public ApiResponse<PagedResponse<ProductResponse>> getProductsByMerchantUuid(
            @PathVariable String merchantUuid,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        PagedResponse<ProductResponse> response = productService.getProductsByMerchantUuid(merchantUuid, page, size);
        return ApiResponse.success("Products fetched successfully", response);
    }

    @GetMapping("/merchant/{merchantUuid}/all")
    public ApiResponse<List<ProductResponse>> getAllProductsByMerchantUuid(@PathVariable String merchantUuid) {
        List<ProductResponse> response = productService.getAllProductsByMerchantUuid(merchantUuid);
        return ApiResponse.success("Products fetched successfully", response);
    }

    @GetMapping("/search")
    public ApiResponse<PagedResponse<ProductResponse>> searchProducts(
            @RequestParam(required = false) String keyword,
            @RequestParam(required = false) BigDecimal minPrice,
            @RequestParam(required = false) BigDecimal maxPrice,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        
        PagedResponse<ProductResponse> response;
        if (minPrice != null && maxPrice != null) {
            response = productService.searchByPriceRange(minPrice, maxPrice, page, size);
        } else if (keyword != null && !keyword.isEmpty()) {
            response = productService.searchProducts(keyword, page, size);
        } else {
            response = productService.getAllProducts(page, size, "createdAt", "DESC");
        }
        return ApiResponse.success("Products fetched successfully", response);
    }

    @PatchMapping("/{id}/stock")
    @PreAuthorize("hasAnyRole('MERCHANT', 'ADMIN')")
    public ApiResponse<ProductResponse> updateStock(@PathVariable String id, @Valid @RequestBody StockUpdateRequest request) {
        ProductResponse response = productService.updateStock(id, request);
        return ApiResponse.success("Product stock updated successfully", response);
    }

    @PatchMapping("/{id}/status")
    @PreAuthorize("hasRole('ADMIN')")
    public ApiResponse<ProductResponse> updateStatus(@PathVariable String id, @Valid @RequestBody StatusUpdateRequest request) {
        ProductResponse response = productService.updateStatus(id, request);
        return ApiResponse.success("Product status updated successfully", response);
    }
}
