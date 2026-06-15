package com.springmart.product.service.impl;

import com.springmart.product.dto.request.CategoryRequest;
import com.springmart.product.dto.response.CategoryResponse;
import com.springmart.product.entity.Category;
import com.springmart.product.exception.DuplicateResourceException;
import com.springmart.product.exception.ResourceNotFoundException;
import com.springmart.product.mapper.CategoryMapper;
import com.springmart.product.repository.CategoryRepository;
import com.springmart.product.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;

    @Override
    public CategoryResponse createCategory(CategoryRequest request) {
        if (categoryRepository.existsByName(request.getName())) {
            throw new DuplicateResourceException("Category already exists with name: " + request.getName());
        }
        if (request.getParentCategoryId() != null && !categoryRepository.existsById(request.getParentCategoryId())) {
            throw new ResourceNotFoundException("Parent Category not found with id: " + request.getParentCategoryId());
        }
        
        Category category = categoryMapper.toEntity(request);
        Category savedCategory = categoryRepository.save(category);
        return categoryMapper.toResponse(savedCategory);
    }

    @Override
    public CategoryResponse updateCategory(String id, CategoryRequest request) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Category not found with id: " + id));

        if (!category.getName().equals(request.getName()) && categoryRepository.existsByName(request.getName())) {
            throw new DuplicateResourceException("Category already exists with name: " + request.getName());
        }

        if (request.getParentCategoryId() != null && !categoryRepository.existsById(request.getParentCategoryId())) {
            throw new ResourceNotFoundException("Parent Category not found with id: " + request.getParentCategoryId());
        }

        categoryMapper.updateEntityFromRequest(request, category);
        Category updatedCategory = categoryRepository.save(category);
        return categoryMapper.toResponse(updatedCategory);
    }

    @Override
    public void deleteCategory(String id) {
        if (!categoryRepository.existsById(id)) {
            throw new ResourceNotFoundException("Category not found with id: " + id);
        }
        categoryRepository.deleteById(id);
    }

    @Override
    public CategoryResponse getCategoryById(String id) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Category not found with id: " + id));
        return categoryMapper.toResponse(category);
    }

    @Override
    public List<CategoryResponse> getAllCategories() {
        List<Category> categories = categoryRepository.findAll();
        return categoryMapper.toResponseList(categories);
    }
}
