package com.orderservice.orderservice.service;

import com.orderservice.orderservice.dto.cart.CartItemDto;
import com.orderservice.orderservice.entity.CartItem;
import org.springframework.stereotype.Service;

import java.util.HashMap;

@Service
public class CartServiceImpl implements CartService{

    public static HashMap<Long, CartItem> cart = new HashMap<>();

    @Override
    public HashMap<Long, CartItem> addToCart(CartItem cartItem) {
        CartItem item = cart.putIfAbsent(cartItem.getProductId(), cartItem);
        if (cartItem.getQuantity() <= 0) {
            throw new RuntimeException("quantity must be greater than 0!");
        }
        if (item != null) {
            item.setQuantity(item.getQuantity() + cartItem.getQuantity());
        }
        return cart;
    }

    @Override
    public void clear() {
        cart.clear();
    }

    @Override
    public HashMap<Long, CartItem> getDetail() {
        return cart;
    }

    @Override
    public HashMap<Long, CartItem> updateCart(CartItemDto cartItemDto) {
        CartItem itemFound = cart.get(cartItemDto.getProductId());
        if (cartItemDto.getQuantity() <= 0) {
            throw new RuntimeException("quantity must be greater than 0!");
        }
        if (itemFound == null){
            throw new RuntimeException("Cart item not found!");
        }
        itemFound.setQuantity(cartItemDto.getQuantity());
        return cart;
    }
}
