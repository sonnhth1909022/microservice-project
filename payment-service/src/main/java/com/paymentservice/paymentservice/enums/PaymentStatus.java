package com.paymentservice.paymentservice.enums;

public enum PaymentStatus {
    PENDING, // Đang đợi xử lý thanh toán
    PAID, // Thanh toán thành công
    NOT_ENOUGH_FUND, // Thanh toán thất bại do không đủ tiền
    REFUND, // Order được hoàn tiền vì kho không đủ hàng
    WALLET_NOT_FOUND // Không tìm thấy ví
}
