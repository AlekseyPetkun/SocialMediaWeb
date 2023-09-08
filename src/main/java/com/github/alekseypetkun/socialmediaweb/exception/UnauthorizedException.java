package com.github.alekseypetkun.socialmediaweb.exception;

public class UnauthorizedException extends RuntimeException{

    protected String errorCode;

    public UnauthorizedException(String message, String errorCode) {
        super(message);
        this.errorCode = errorCode;
    }
}
