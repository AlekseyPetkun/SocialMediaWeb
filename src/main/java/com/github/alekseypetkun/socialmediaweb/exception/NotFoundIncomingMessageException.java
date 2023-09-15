package com.github.alekseypetkun.socialmediaweb.exception;

public class NotFoundIncomingMessageException extends NotFoundException {

    private final Long id;

    public NotFoundIncomingMessageException(Long id) {
        this.id = id;
    }

    public NotFoundIncomingMessageException() {
        this.id = null;
    }

    @Override
    public String getMessage() {

        return id != null
                ? "Сообщение от пользователя с id: " + id + " не найдено!"
                : "Вам еще не отправляли сообщения!";
    }
}
