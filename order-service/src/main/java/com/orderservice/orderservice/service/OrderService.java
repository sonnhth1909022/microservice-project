package com.orderservice.orderservice.service;

import com.orderservice.orderservice.entity.Order;

import java.util.List;
import java.util.Optional;

public interface OrderService {
    Order saveOrder(Order order);
    Order getOrderById(String orderId);
    List<Order> findAllOrdersByUserId(String userId);
}
