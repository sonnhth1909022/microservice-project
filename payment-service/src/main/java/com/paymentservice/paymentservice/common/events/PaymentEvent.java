package com.paymentservice.paymentservice.common.events;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class PaymentEvent {
    private String orderId;
    private String userId;
    private double totalPrice;
    private String paymentStatus;
    private String orderStatus;
    private String message;
    private String queueName;
}
