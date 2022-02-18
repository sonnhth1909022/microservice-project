package com.orderservice.orderservice.service;

import com.orderservice.orderservice.entity.Cart;
import com.orderservice.orderservice.repository.CartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CartServiceImpl implements CartService{

    @Autowired
    private CartRepository cartRepository;

    @Override
    public Cart saveCart(Cart cart) {
        return cartRepository.save(cart);
    }

    @Override
    public Optional<Cart> findCartByUserId(String userId) {
        return cartRepository.findByUserId(userId);
    }

    @Override
    public Optional<Cart> findCartById(long id) {
        return cartRepository.findById(id);
    }
}
