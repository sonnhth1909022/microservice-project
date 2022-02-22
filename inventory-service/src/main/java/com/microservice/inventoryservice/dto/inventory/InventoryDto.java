package com.microservice.inventoryservice.dto.inventory;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class InventoryDto {
    private long productId;
    private int stockQuantity;
}
