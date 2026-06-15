package com.springmart.product.mapper;

import com.springmart.product.dto.request.CategoryRequest;
import com.springmart.product.dto.response.CategoryResponse;
import com.springmart.product.entity.Category;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface CategoryMapper {

    Category toEntity(CategoryRequest request);

    CategoryResponse toResponse(Category category);

    List<CategoryResponse> toResponseList(List<Category> categories);

    void updateEntityFromRequest(CategoryRequest request, @MappingTarget Category category);
}
