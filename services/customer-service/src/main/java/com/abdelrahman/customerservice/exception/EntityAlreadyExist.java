package com.abdelrahman.customerservice.exception;

public class EntityAlreadyExist extends RuntimeException{
    public EntityAlreadyExist(String message) {
        super(message);
    }
}
