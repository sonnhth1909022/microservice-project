package com.orderservice.orderservice.service;

import com.orderservice.orderservice.entity.OrderDetail;
import com.orderservice.orderservice.repository.OrderDetailRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrderDetailServiceImpl implements OrderDetailService{

    @Autowired
    private OrderDetailRepository orderDetailRepository;

    @Override
    public OrderDetail saveOrderDetail(OrderDetail orderDetail) {
        return orderDetailRepository.save(orderDetail);
    }
}
