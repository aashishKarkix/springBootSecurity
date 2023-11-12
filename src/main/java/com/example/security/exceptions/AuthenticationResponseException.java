package com.example.security.exceptions;

import lombok.Getter;

    @Getter
    public class AuthenticationResponseException extends Exception{
        private final String reason;

        public AuthenticationResponseException(String reason) {
            super(reason);
            this.reason = reason;
        }
}
