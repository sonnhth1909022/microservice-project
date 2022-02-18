package com.orderservice.orderservice.dto.cart;

import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class CartUpdateDto {
    private long productId;
    private int quantity;
}
