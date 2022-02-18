package com.orderservice.orderservice.repository;

import com.orderservice.orderservice.entity.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CartItemRepository extends JpaRepository<CartItem, Long> {
    Optional<CartItem> findByProductId(long productId);
    List<CartItem> findAllByCartId(long cartId);
    Optional<CartItem> findByCartIdAndProductId(long cartId, long productId);
    List<CartItem> deleteAllByCartId(long id);
}
