package com.example.security.exceptions;

import lombok.Getter;

@Getter
public class AuthenticationRequestException extends Exception {
    private final String reason;

    public AuthenticationRequestException(String reason) {
        super(reason);
        this.reason = reason;
    }
}
