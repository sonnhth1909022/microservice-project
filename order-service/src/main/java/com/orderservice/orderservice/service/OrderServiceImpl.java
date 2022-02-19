package com.orderservice.orderservice.service;

import com.orderservice.orderservice.entity.Order;
import com.orderservice.orderservice.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class OrderServiceImpl implements OrderService{

    @Autowired
    private OrderRepository orderRepository;

    @Override
    public Order saveOrder(Order order) {
        return orderRepository.save(order);
    }

    @Override
    public Order getOrderById(String orderId) {
        return orderRepository.getByOrderId(orderId);
    }

    @Override
    public Optional<Order> findOrderById(String orderId) {
        return orderRepository.findByOrderId(orderId);
    }

    @Override
    public Order findByOrderIdForRabbit(String orderId) {
        return orderRepository.findByOrderId(orderId).orElse(null);
    }

    @Override
    public List<Order> findAllOrdersByUserId(String userId) {
        return orderRepository.findAllByUserId(userId);
    }
}
