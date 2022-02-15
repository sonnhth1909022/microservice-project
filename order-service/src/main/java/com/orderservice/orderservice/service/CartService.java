package com.orderservice.orderservice.service;

import com.orderservice.orderservice.dto.cart.CartItemDto;
import com.orderservice.orderservice.entity.CartItem;

import java.util.HashMap;
import java.util.UUID;

public interface CartService {
    HashMap<Long, CartItem> addToCart(CartItem cartItem);

    void clear();

    HashMap<Long, CartItem> getDetail();
    HashMap<Long, CartItem> updateCart(CartItemDto cartItemDto);
}
