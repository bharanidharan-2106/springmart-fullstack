package com.springmart.cart.mapper;

import com.springmart.cart.dto.response.CartItemResponse;
import com.springmart.cart.dto.response.CartResponse;
import com.springmart.cart.dto.response.CartSummaryResponse;
import com.springmart.cart.entity.Cart;
import com.springmart.cart.entity.CartItem;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CartMapper {

    @Mapping(source = "id", target = "cartId")
    CartResponse toCartResponse(Cart cart);

    CartItemResponse toCartItemResponse(CartItem cartItem);

    CartSummaryResponse toCartSummaryResponse(Cart cart);
}
