package com.github.alekseypetkun.socialmediaweb.exception;

public class AuthException extends RuntimeException{

    protected String errorCode;

    public AuthException(String message, String errorCode) {
        super(message);
        this.errorCode = errorCode;
    }
}
