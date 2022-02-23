package com.orderservice.orderservice.repository;

import com.orderservice.orderservice.entity.OrderDetail;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderDetailRepository extends JpaRepository<OrderDetail, Long> {
    List<OrderDetail> findAllByOrderId(String orderId);
}
