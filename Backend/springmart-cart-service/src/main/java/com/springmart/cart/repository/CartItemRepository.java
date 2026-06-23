package com.springmart.cart.repository;

import com.springmart.cart.entity.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CartItemRepository extends JpaRepository<CartItem, Long> {
    Optional<CartItem> findByCartIdAndProductId(Long cartId, Long productId);
    void deleteByCartIdAndProductId(Long cartId, Long productId);
    void deleteAllByCartId(Long cartId);
}
