package com.abdelrahman.orderservice.dto.kafka;

public enum OrderStatus {
    CREATED,
    COMPLETED,
    CANCELED,
    WAITING_FOR_DELIVERY    // Order paid, waiting for delivery,


}
