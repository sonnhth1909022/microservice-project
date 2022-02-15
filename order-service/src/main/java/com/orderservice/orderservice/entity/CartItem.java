package com.orderservice.orderservice.entity;

import lombok.*;

import java.math.BigDecimal;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class CartItem {
    private long productId;
    private String productName;
    private int quantity;
    private String thumbnail;
    private double unitPrice;
}
