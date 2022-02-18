package com.orderservice.orderservice.repository;

import com.orderservice.orderservice.entity.Cart;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CartRepository extends JpaRepository<Cart, String> {
    Optional<Cart> findByUserId(String userId);
    Optional<Cart> findById(long id);
}
