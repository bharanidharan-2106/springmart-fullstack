package com.springmart.product.mapper;

import com.springmart.product.dto.request.BrandRequest;
import com.springmart.product.dto.response.BrandResponse;
import com.springmart.product.entity.Brand;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface BrandMapper {

    Brand toEntity(BrandRequest request);

    BrandResponse toResponse(Brand brand);

    List<BrandResponse> toResponseList(List<Brand> brands);

    void updateEntityFromRequest(BrandRequest request, @MappingTarget Brand brand);
}
