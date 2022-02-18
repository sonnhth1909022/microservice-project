package com.orderservice.orderservice.dto.order;

import lombok.*;

import java.util.Set;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class OrderDto {
    private String orderId;

    private String userId;
    private double totalPrice;
    private String address;
    private String name;
    private String phone;
    private String email;
    private String paymentStatus;
    private String inventoryStatus;
    private String orderStatus;
    private Set<OrderDetailDto> orderDetails;
}
