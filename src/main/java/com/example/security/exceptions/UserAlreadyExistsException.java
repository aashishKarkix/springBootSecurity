package com.example.security.exceptions;

public class UserAlreadyExistsException extends RuntimeException{
    private final String reason;

    public UserAlreadyExistsException(String reason) {
        super(reason);
        this.reason = reason;
    }
}
