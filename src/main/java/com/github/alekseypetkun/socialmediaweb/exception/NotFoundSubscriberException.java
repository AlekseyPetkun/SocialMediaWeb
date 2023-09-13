package com.github.alekseypetkun.socialmediaweb.exception;

public class NotFoundSubscriberException extends NotFoundException{

    private final Long id;

    public NotFoundSubscriberException(Long id) {
        this.id = id;
    }

    @Override
    public String getMessage() {
        return String.format("Подписчик с id: " + id + " не найден! " +
                "Или статус уже существует!");
    }
}
