package com.springmart.cart.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductResponse {
    private Long id;
    private String name;
    private String sku;
    private BigDecimal price;
    private Integer stockQuantity;
    private Boolean active;
    private Boolean approved;
    private String coverImageUrl;
}
