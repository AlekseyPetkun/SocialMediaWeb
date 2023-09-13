package com.github.alekseypetkun.socialmediaweb.exception;


public class NotFoundUserException extends NotFoundException{

    private final String username;
    private final long userId;

    public NotFoundUserException(String username) {
        this.username = username;
        this.userId = 0;
    }

    public NotFoundUserException(long userId) {
        this.username = null;
        this.userId = userId;
    }

    @Override
    public String getMessage() {

        return username != null
                ? "Пользователь с логином: " + username + " не найден!"
                : "Пользователь с id: " + userId + " не найден!";
    }
}
