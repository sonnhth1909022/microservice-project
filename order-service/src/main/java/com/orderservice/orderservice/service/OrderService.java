package com.orderservice.orderservice.service;

import com.orderservice.orderservice.entity.Order;

import java.util.List;
import java.util.Optional;

public interface OrderService {
    Order saveOrder(Order order);
    Order getOrderById(String orderId);
    Optional<Order> findOrderById(String orderId);
    Order findByOrderIdForRabbit(String orderId);
    List<Order> findAllOrdersByUserId(String userId);
}
