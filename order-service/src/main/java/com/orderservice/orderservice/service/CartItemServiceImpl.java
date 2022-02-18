package com.orderservice.orderservice.service;

import com.orderservice.orderservice.entity.CartItem;
import com.orderservice.orderservice.repository.CartItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class CartItemServiceImpl implements CartItemService{

    @Autowired
    private CartItemRepository cartItemRepository;

    @Override
    public CartItem saveCartItem(CartItem cartItem) {
        return cartItemRepository.save(cartItem);
    }

    @Override
    public Optional<CartItem> findCartItemByProductId(long productId) {
        return cartItemRepository.findByProductId(productId);
    }

    @Override
    public List<CartItem> findAllCarItemsByCartId(long cartId) {
        return cartItemRepository.findAllByCartId(cartId);
    }

    @Override
    public Optional<CartItem> findCartItemByCartIdAndProductId(long cartId, long productId) {
        return cartItemRepository.findByCartIdAndProductId(cartId, productId);
    }

    @Override
    public void deleteCartItemById(long id) {
        this.cartItemRepository.deleteById(id);
    }

    @Override
    public void deleteAllCartItemsByCartId(long id) {
        this.cartItemRepository.deleteAllByCartId(id);
    }
}
