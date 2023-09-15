package com.github.alekseypetkun.socialmediaweb.exception;

public class NotFoundSendMessageException extends NotFoundException {

    private final Long id;

    public NotFoundSendMessageException(Long id) {
        this.id = id;
    }

    public NotFoundSendMessageException() {
        this.id = null;
    }

    @Override
    public String getMessage() {

        return id != null
                ? "Сообщение пользователю с id: " + id + " не найдено!"
                : "Вы еще не отправляли сообщения этому пользователю";
    }
}
