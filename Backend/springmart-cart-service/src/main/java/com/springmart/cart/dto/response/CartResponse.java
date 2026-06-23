package com.springmart.cart.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CartResponse {
    private Long cartId;
    private Long userId;
    private Integer totalItems;
    private Integer totalQuantity;
    private BigDecimal totalAmount;
    private List<CartItemResponse> items;
}
