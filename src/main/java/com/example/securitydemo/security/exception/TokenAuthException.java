package com.example.securitydemo.security.exception;

import org.springframework.security.core.AuthenticationException;

public class TokenAuthException extends AuthenticationException {
    public TokenAuthException(String msg, Throwable cause) {
        super(msg, cause);
    }

    public TokenAuthException(String msg) {
        super(msg);
    }
}
