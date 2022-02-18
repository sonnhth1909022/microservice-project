package com.orderservice.orderservice.service;

import com.orderservice.orderservice.entity.Cart;

import java.util.Optional;

public interface CartService {
    Cart saveCart(Cart cart);
    Optional<Cart> findCartByUserId(String userId);
    Optional<Cart> findCartById(long id);
}
