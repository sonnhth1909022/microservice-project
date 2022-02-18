package com.orderservice.orderservice.dto.cart;

import lombok.*;

import java.util.Set;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class CartDto {
    private String userName;
    private Set<CartItemDto> cartItems;
}
