package com.orderservice.orderservice.repository;

import com.orderservice.orderservice.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface OrderRepository extends JpaRepository<Order, String> {
    Order getByOrderId(String orderId);
    List<Order> findAllByUserId(String userId);
    Optional<Order> findByOrderId(String orderId);
}
