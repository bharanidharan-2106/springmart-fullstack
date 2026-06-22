package com.springmart.product.service;

import com.springmart.product.dto.request.ProductRequest;
import com.springmart.product.dto.request.StatusUpdateRequest;
import com.springmart.product.dto.request.StockUpdateRequest;
import com.springmart.product.dto.response.PagedResponse;
import com.springmart.product.dto.response.ProductResponse;

import java.math.BigDecimal;
import java.util.List;

public interface ProductService {
    ProductResponse createProduct(ProductRequest request);
    ProductResponse updateProduct(String id, ProductRequest request);
    void deleteProduct(String id);
    ProductResponse getProductById(String id);
    ProductResponse getProductBySku(String sku);
    PagedResponse<ProductResponse> getAllProducts(int page, int size, String sortBy, String sortDir);
    PagedResponse<ProductResponse> getProductsByCategory(String categoryId, int page, int size);
    PagedResponse<ProductResponse> getProductsByBrand(String brandId, int page, int size);
    PagedResponse<ProductResponse> getProductsByMerchantUuid(String merchantUuid, int page, int size);
    List<ProductResponse> getAllProductsByMerchantUuid(String merchantUuid);
    PagedResponse<ProductResponse> searchProducts(String keyword, int page, int size);
    PagedResponse<ProductResponse> searchByPriceRange(BigDecimal min, BigDecimal max, int page, int size);
    ProductResponse updateStock(String id, StockUpdateRequest request);
    ProductResponse updateStatus(String id, StatusUpdateRequest request);
}
