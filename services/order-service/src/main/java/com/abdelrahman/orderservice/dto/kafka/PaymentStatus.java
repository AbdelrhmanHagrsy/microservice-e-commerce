package com.abdelrahman.orderservice.dto.kafka;

public enum PaymentStatus {
    PENDING,       // Payment is pending
    COMPLETED,     // Payment has been completed
    FAILED,        // Payment has failed
    AWAITING_COD   // Payment will be made in cash on delivery
}
