package com.springmart.product.mapper;

import com.springmart.product.dto.request.ProductRequest;
import com.springmart.product.dto.response.ProductResponse;
import com.springmart.product.entity.Product;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ProductMapper {

    Product toEntity(ProductRequest request);

    ProductResponse toResponse(Product product);

    List<ProductResponse> toResponseList(List<Product> products);

    void updateEntityFromRequest(ProductRequest request, @MappingTarget Product product);
}
