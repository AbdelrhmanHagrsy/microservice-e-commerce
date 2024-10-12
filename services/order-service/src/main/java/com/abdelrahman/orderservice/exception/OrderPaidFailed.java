package com.abdelrahman.orderservice.exception;

public class OrderPaidFailed extends RuntimeException{
    public OrderPaidFailed(String message) {
        super(message);
    }
}
