package com.orderservice.orderservice.service;

import com.orderservice.orderservice.entity.CartItem;

import java.util.List;
import java.util.Optional;

public interface CartItemService {
    CartItem saveCartItem(CartItem cartItem);
    Optional<CartItem> findCartItemByProductId(long productId);
    List<CartItem> findAllCartItemsByCartId(long cartId);
    Optional<CartItem> findCartItemByCartIdAndProductId(long cartId, long productId);
    void deleteCartItemById(long id);
    void deleteAllCartItemsByCartId(long id);
}
