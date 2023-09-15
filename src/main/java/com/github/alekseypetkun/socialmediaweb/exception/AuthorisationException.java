package com.github.alekseypetkun.socialmediaweb.exception;

public class AuthorisationException extends RuntimeException{

    protected String errorCode;

    public AuthorisationException(String message, String errorCode) {
        super(message);
        this.errorCode = errorCode;
    }
}
