package com.orderservice.orderservice.dto.order;

import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class OrderDetailDto {
    private long id;
    private String orderId;
    private long productId;
    private String productName;
    private int quantity;
    private double unitPrice;
}
