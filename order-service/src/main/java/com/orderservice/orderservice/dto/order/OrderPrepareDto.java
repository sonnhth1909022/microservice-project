package com.orderservice.orderservice.dto.order;

import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class OrderPrepareDto {
    private String name;
    private String phone;
    private String email;
    private String address;
}
