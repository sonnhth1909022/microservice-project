package com.microservice.inventoryservice.common.events;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class OrderDetailEvent {
    private long productId;
    private int quantity;
}
