package com.microservice.inventoryservice.enums;

public enum InventoryStatus {
    PENDING, //Đang đợi kiểm tra kho
    OUT_OF_STOCK, // Hết hàng
    SUCCESS // Hàng đủ, check đơn thành công
}
