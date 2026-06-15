package com.springmart.product.controller;

import com.springmart.product.dto.request.BrandRequest;
import com.springmart.product.dto.response.ApiResponse;
import com.springmart.product.dto.response.BrandResponse;
import com.springmart.product.service.BrandService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/brands")
@RequiredArgsConstructor
public class BrandController {

    private final BrandService brandService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasRole('ADMIN')")
    public ApiResponse<BrandResponse> createBrand(@Valid @RequestBody BrandRequest request) {
        BrandResponse response = brandService.createBrand(request);
        return ApiResponse.success("Brand created successfully", response);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ApiResponse<BrandResponse> updateBrand(@PathVariable String id, @Valid @RequestBody BrandRequest request) {
        BrandResponse response = brandService.updateBrand(id, request);
        return ApiResponse.success("Brand updated successfully", response);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ApiResponse<Void> deleteBrand(@PathVariable String id) {
        brandService.deleteBrand(id);
        return ApiResponse.success("Brand deleted successfully", null);
    }

    @GetMapping("/{id}")
    public ApiResponse<BrandResponse> getBrandById(@PathVariable String id) {
        BrandResponse response = brandService.getBrandById(id);
        return ApiResponse.success("Brand fetched successfully", response);
    }

    @GetMapping
    public ApiResponse<List<BrandResponse>> getAllBrands() {
        List<BrandResponse> response = brandService.getAllBrands();
        return ApiResponse.success("Brands fetched successfully", response);
    }
}
