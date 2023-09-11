package com.github.alekseypetkun.socialmediaweb.exception;

public class AuthenticationException extends RuntimeException{

    protected String errorCode;

    public AuthenticationException(String message, String errorCode) {
        super(message);
        this.errorCode = errorCode;
    }
}
