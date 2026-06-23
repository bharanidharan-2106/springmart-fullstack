package com.springmart.cart.service;

import com.springmart.cart.client.ProductServiceClient;
import com.springmart.cart.dto.request.AddToCartRequest;
import com.springmart.cart.dto.request.UpdateCartItemRequest;
import com.springmart.cart.dto.response.CartResponse;
import com.springmart.cart.dto.response.CartSummaryResponse;
import com.springmart.cart.dto.response.ProductResponse;
import com.springmart.cart.entity.Cart;
import com.springmart.cart.entity.CartItem;
import com.springmart.cart.exception.BadRequestException;
import com.springmart.cart.exception.ResourceNotFoundException;
import com.springmart.cart.mapper.CartMapper;
import com.springmart.cart.repository.CartItemRepository;
import com.springmart.cart.repository.CartRepository;
import feign.FeignException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class CartServiceImpl implements CartService {

    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;
    private final ProductServiceClient productServiceClient;
    private final CartMapper cartMapper;

    @Override
    @Transactional
    public CartResponse addToCart(Long userId, AddToCartRequest request) {
        log.info("Adding product {} to cart for user {}", request.getProductId(), userId);
        
        ProductResponse product = validateAndGetProduct(request.getProductId(), request.getQuantity());

        Cart cart = getOrCreateCart(userId);

        Optional<CartItem> existingItemOpt = cartItemRepository.findByCartIdAndProductId(cart.getId(), product.getId());

        if (existingItemOpt.isPresent()) {
            CartItem existingItem = existingItemOpt.get();
            existingItem.setQuantity(existingItem.getQuantity() + request.getQuantity());
            existingItem.calculateLineTotal();
            cartItemRepository.save(existingItem);
        } else {
            CartItem newItem = CartItem.builder()
                    .cart(cart)
                    .productId(product.getId())
                    .productName(product.getName())
                    .productImage(product.getCoverImageUrl())
                    .sku(product.getSku())
                    .price(product.getPrice())
                    .quantity(request.getQuantity())
                    .build();
            newItem.calculateLineTotal();
            cart.addItem(newItem);
        }

        cart.calculateTotals();
        Cart savedCart = cartRepository.save(cart);

        return cartMapper.toCartResponse(savedCart);
    }

    @Override
    @Transactional(readOnly = true)
    public CartResponse getCart(Long userId) {
        log.info("Fetching cart for user {}", userId);
        Cart cart = getOrCreateCart(userId);
        return cartMapper.toCartResponse(cart);
    }

    @Override
    @Transactional
    public CartResponse updateCartItem(Long userId, Long cartItemId, UpdateCartItemRequest request) {
        log.info("Updating cart item {} for user {} to quantity {}", cartItemId, userId, request.getQuantity());
        
        Cart cart = getCartByUserId(userId);
        
        CartItem cartItem = cartItemRepository.findById(cartItemId)
                .orElseThrow(() -> new ResourceNotFoundException("Cart item not found"));

        if (!cartItem.getCart().getId().equals(cart.getId())) {
            throw new BadRequestException("Cart item does not belong to user cart");
        }

        validateAndGetProduct(cartItem.getProductId(), request.getQuantity());

        cartItem.setQuantity(request.getQuantity());
        cartItem.calculateLineTotal();
        
        cart.calculateTotals();
        Cart savedCart = cartRepository.save(cart);

        return cartMapper.toCartResponse(savedCart);
    }

    @Override
    @Transactional
    public CartResponse removeCartItem(Long userId, Long cartItemId) {
        log.info("Removing cart item {} for user {}", cartItemId, userId);
        
        Cart cart = getCartByUserId(userId);
        
        CartItem cartItem = cartItemRepository.findById(cartItemId)
                .orElseThrow(() -> new ResourceNotFoundException("Cart item not found"));

        if (!cartItem.getCart().getId().equals(cart.getId())) {
            throw new BadRequestException("Cart item does not belong to user cart");
        }

        cart.removeItem(cartItem);
        cartItemRepository.delete(cartItem);
        
        Cart savedCart = cartRepository.save(cart);

        return cartMapper.toCartResponse(savedCart);
    }

    @Override
    @Transactional
    public void clearCart(Long userId) {
        log.info("Clearing cart for user {}", userId);
        Cart cart = getCartByUserId(userId);
        cartItemRepository.deleteAllByCartId(cart.getId());
        cart.getItems().clear();
        cart.calculateTotals();
        cartRepository.save(cart);
    }

    @Override
    @Transactional(readOnly = true)
    public CartSummaryResponse getCartSummary(Long userId) {
        Cart cart = getOrCreateCart(userId);
        return cartMapper.toCartSummaryResponse(cart);
    }

    private Cart getOrCreateCart(Long userId) {
        return cartRepository.findByUserId(userId).orElseGet(() -> {
            Cart newCart = Cart.builder()
                    .userId(userId)
                    .totalItems(0)
                    .totalQuantity(0)
                    .totalAmount(BigDecimal.ZERO)
                    .build();
            return cartRepository.save(newCart);
        });
    }
    
    private Cart getCartByUserId(Long userId) {
        return cartRepository.findByUserId(userId)
                .orElseThrow(() -> new ResourceNotFoundException("Cart not found for user"));
    }

    private ProductResponse validateAndGetProduct(Long productId, Integer requestedQuantity) {
        try {
            ProductResponse product = productServiceClient.getProductById(productId);
            
            if (product == null || !Boolean.TRUE.equals(product.getActive()) || !Boolean.TRUE.equals(product.getApproved())) {
                throw new BadRequestException("Product is not available");
            }
            
            if (product.getStockQuantity() < requestedQuantity) {
                throw new BadRequestException("Not enough stock available for product: " + product.getName());
            }
            
            return product;
        } catch (FeignException.NotFound e) {
            throw new ResourceNotFoundException("Product not found with id: " + productId);
        } catch (FeignException e) {
            log.error("Error communicating with product service", e);
            throw new RuntimeException("Error validating product", e);
        }
    }
}
