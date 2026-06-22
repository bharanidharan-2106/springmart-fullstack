package com.springmart.product.dto.response;

import com.springmart.product.enums.ProductStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductResponse {
    private String id;
    private String merchantUuid;
    private String productName;
    private String sku;
    private String slug;
    private String shortDescription;
    private String description;
    private String categoryId;
    private String categoryName;
    private String brandId;
    private String brandName;
    private BigDecimal price;
    private BigDecimal discountedPrice;
    private Integer quantity;
    private List<String> productImages;
    private Map<String, String> specifications;
    private Double averageRating;
    private Integer reviewCount;
    private ProductStatus status;
    private boolean active;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
