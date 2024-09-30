package com.abdelrahman.orderservice.exception;

public class OrderConflictExceotion extends RuntimeException{
    public OrderConflictExceotion(String message) {
        super(message);
    }
}
