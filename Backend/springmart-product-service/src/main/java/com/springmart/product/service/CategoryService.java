package com.springmart.product.service;

import com.springmart.product.dto.request.CategoryRequest;
import com.springmart.product.dto.response.CategoryResponse;

import java.util.List;

public interface CategoryService {
    CategoryResponse createCategory(CategoryRequest request);
    CategoryResponse updateCategory(String id, CategoryRequest request);
    void deleteCategory(String id);
    CategoryResponse getCategoryById(String id);
    List<CategoryResponse> getAllCategories();
}
