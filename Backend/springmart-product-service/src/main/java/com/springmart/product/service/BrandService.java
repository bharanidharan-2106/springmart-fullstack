package com.springmart.product.service;

import com.springmart.product.dto.request.BrandRequest;
import com.springmart.product.dto.response.BrandResponse;

import java.util.List;

public interface BrandService {
    BrandResponse createBrand(BrandRequest request);
    BrandResponse updateBrand(String id, BrandRequest request);
    void deleteBrand(String id);
    BrandResponse getBrandById(String id);
    List<BrandResponse> getAllBrands();
}
