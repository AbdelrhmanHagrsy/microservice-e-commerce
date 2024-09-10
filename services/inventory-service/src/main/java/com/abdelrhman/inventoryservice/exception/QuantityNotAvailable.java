package com.abdelrhman.inventoryservice.exception;

public class QuantityNotAvailable extends RuntimeException{
    public QuantityNotAvailable(String message) {
        super(message);
    }
}
