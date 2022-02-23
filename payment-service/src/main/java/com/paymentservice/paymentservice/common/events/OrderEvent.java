package com.paymentservice.paymentservice.common.events;

import lombok.*;

import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class OrderEvent {
    private String orderId;
    private String userId;
    private double totalPrice;
    private String paymentStatus;
    private String orderStatus;
    private String inventoryStatus;
    private String message;
    private String queueName;
    private Set<OrderDetailEvent> orderItems;
}
