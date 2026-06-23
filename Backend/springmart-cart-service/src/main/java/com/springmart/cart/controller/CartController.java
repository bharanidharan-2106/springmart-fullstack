package com.springmart.cart.controller;

import com.springmart.cart.dto.request.AddToCartRequest;
import com.springmart.cart.dto.request.UpdateCartItemRequest;
import com.springmart.cart.dto.response.CartResponse;
import com.springmart.cart.dto.response.CartSummaryResponse;
import com.springmart.cart.service.CartService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/cart")
@RequiredArgsConstructor
public class CartController {

    private final CartService cartService;

    @PostMapping("/items")
    @PreAuthorize("hasRole('CUSTOMER')")
    public ResponseEntity<CartResponse> addToCart(
            @RequestHeader("X-User-Id") Long userId,
            @Valid @RequestBody AddToCartRequest request) {
        CartResponse response = cartService.addToCart(userId, request);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping
    @PreAuthorize("hasRole('CUSTOMER')")
    public ResponseEntity<CartResponse> getCart(@RequestHeader("X-User-Id") Long userId) {
        CartResponse response = cartService.getCart(userId);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/items/{cartItemId}")
    @PreAuthorize("hasRole('CUSTOMER')")
    public ResponseEntity<CartResponse> updateCartItem(
            @RequestHeader("X-User-Id") Long userId,
            @PathVariable Long cartItemId,
            @Valid @RequestBody UpdateCartItemRequest request) {
        CartResponse response = cartService.updateCartItem(userId, cartItemId, request);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/items/{cartItemId}")
    @PreAuthorize("hasRole('CUSTOMER')")
    public ResponseEntity<CartResponse> removeCartItem(
            @RequestHeader("X-User-Id") Long userId,
            @PathVariable Long cartItemId) {
        CartResponse response = cartService.removeCartItem(userId, cartItemId);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping
    @PreAuthorize("hasRole('CUSTOMER')")
    public ResponseEntity<Void> clearCart(@RequestHeader("X-User-Id") Long userId) {
        cartService.clearCart(userId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/summary")
    @PreAuthorize("hasRole('CUSTOMER')")
    public ResponseEntity<CartSummaryResponse> getCartSummary(@RequestHeader("X-User-Id") Long userId) {
        CartSummaryResponse response = cartService.getCartSummary(userId);
        return ResponseEntity.ok(response);
    }
}
