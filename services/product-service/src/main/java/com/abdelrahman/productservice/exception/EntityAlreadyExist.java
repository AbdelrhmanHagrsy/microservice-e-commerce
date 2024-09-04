package com.abdelrahman.productservice.exception;

public class EntityAlreadyExist extends RuntimeException{
    public EntityAlreadyExist(String message) {
        super(message);
    }
}
