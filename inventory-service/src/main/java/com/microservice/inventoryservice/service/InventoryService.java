package com.microservice.inventoryservice.service;

import com.microservice.inventoryservice.entity.Inventory;

import java.util.Optional;

public interface InventoryService {
    Inventory saveInventory(Inventory inventory);
    Optional<Inventory> findInventoryByProductId(long productId);
}
