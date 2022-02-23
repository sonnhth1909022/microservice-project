package com.orderservice.orderservice.service;

import com.orderservice.orderservice.entity.OrderDetail;

import java.util.List;

public interface OrderDetailService {
    OrderDetail saveOrderDetail(OrderDetail orderDetail);
    List<OrderDetail> findAllOrderItemsByOrderId(String orderId);
}
