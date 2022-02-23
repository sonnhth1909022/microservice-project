package com.orderservice.orderservice.service;

import com.orderservice.orderservice.entity.OrderDetail;
import com.orderservice.orderservice.repository.OrderDetailRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderDetailServiceImpl implements OrderDetailService{

    @Autowired
    private OrderDetailRepository orderDetailRepository;

    @Override
    public OrderDetail saveOrderDetail(OrderDetail orderDetail) {
        return orderDetailRepository.save(orderDetail);
    }

    @Override
    public List<OrderDetail> findAllOrderItemsByOrderId(String orderId) {
        return orderDetailRepository.findAllByOrderId(orderId);
    }
}
