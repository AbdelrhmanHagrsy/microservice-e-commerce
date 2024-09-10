package com.abdelrahaman.authenticationservice.exception;

public class UserNotActive extends RuntimeException{
    public UserNotActive(String message) {
        super(message);
    }
}
