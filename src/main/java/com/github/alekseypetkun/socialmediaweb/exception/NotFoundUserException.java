package com.github.alekseypetkun.socialmediaweb.exception;

public class NotFoundUserException extends NotFoundException{

    private final String username;

    public NotFoundUserException(String username) {
        this.username = username;
    }

    @Override
    public String getMessage() {
        return "Пользовател с логином: " + username + " не найден!";
    }
}
