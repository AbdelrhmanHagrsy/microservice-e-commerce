package com.abdelrahman.orderservice.exception;

public class OrderDeliveredFailed extends RuntimeException{
    public OrderDeliveredFailed(String message) {
        super(message);
    }
}
