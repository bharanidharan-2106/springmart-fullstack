package com.springmart.cart.service;

import com.springmart.cart.dto.request.AddToCartRequest;
import com.springmart.cart.dto.request.UpdateCartItemRequest;
import com.springmart.cart.dto.response.CartResponse;
import com.springmart.cart.dto.response.CartSummaryResponse;

public interface CartService {
    
    CartResponse addToCart(Long userId, AddToCartRequest request);

    CartResponse getCart(Long userId);

    CartResponse updateCartItem(Long userId, Long cartItemId, UpdateCartItemRequest request);

    CartResponse removeCartItem(Long userId, Long cartItemId);

    void clearCart(Long userId);

    CartSummaryResponse getCartSummary(Long userId);
}
