package com.github.alekseypetkun.socialmediaweb.exception;

public class NotFoundPostException extends NotFoundException{

    private final Long id;

    public NotFoundPostException(Long id) {
        this.id = id;
    }

    @Override
    public String getMessage() {
        return String.format("Пост с id: " + id + " не найден!");
    }
}
