package com.abdelrahman.orderservice.dto.kafka;

public enum DeliveryStatus {
    NOT_DISPATCHED, // Order has not been dispatched yet
    IN_TRANSIT,     // Order is currently being delivered
    DELIVERED,      // Order has been delivered to the customer
    RETURNED        // Order has been returned by the customer
}
