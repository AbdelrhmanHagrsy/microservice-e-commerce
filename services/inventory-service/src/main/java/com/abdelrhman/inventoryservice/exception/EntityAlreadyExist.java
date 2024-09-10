package com.abdelrhman.inventoryservice.exception;

public class EntityAlreadyExist extends RuntimeException{
    public EntityAlreadyExist(String message) {
        super(message);
    }
}
