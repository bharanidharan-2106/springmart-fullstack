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
public class CartSummaryResponse {
    private Integer totalItems;
    private Integer totalQuantity;
    private BigDecimal totalAmount;
}
