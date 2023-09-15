package com.github.alekseypetkun.socialmediaweb.service;

import com.github.alekseypetkun.socialmediaweb.dto.MessageRequest;
import com.github.alekseypetkun.socialmediaweb.dto.MessageResponse;
import com.github.alekseypetkun.socialmediaweb.dto.ResponseWrapperMessage;

/**
 * Сервис по работе с сообщениями
 */
public interface MessageService {

    /**
     * Получить все входящие сообщения от определенного пользователя
     *
     * @param pageNumber параметр начальной страницы
     * @param pageSize   параметр конечной страницы
     * @param toUserId   получатель сообщений
     * @param fromUserId автор сообщений
     * @return входящие сообщения от определенного пользователя
     */
    ResponseWrapperMessage getAllIncomingMessagesById(int pageNumber, int pageSize, Long toUserId, Long fromUserId);

    /**
     * Получить все исходящие сообщения определенному пользователю
     *
     * @param pageNumber параметр начальной страницы
     * @param pageSize   параметр конечной страницы
     * @param fromUserId автор сообщений
     * @param toUserId   получатель сообщений
     * @return исходящие сообщения определенному пользователю
     */
    ResponseWrapperMessage getAllSendMessagesById(int pageNumber, int pageSize, Long fromUserId, Long toUserId);

    /**
     * Получить все входящие сообщения
     *
     * @param pageNumber параметр начальной страницы
     * @param pageSize   параметр конечной страницы
     * @param toUserId   получатель сообщений
     * @return входящие сообщения
     */
    ResponseWrapperMessage getAllIncomingMessages(int pageNumber, int pageSize, Long toUserId);

    /**
     * Получить все исходящие сообщения
     *
     * @param pageNumber параметр начальной страницы
     * @param pageSize   параметр конечной страницы
     * @param fromUserId автор сообщений
     * @return исходящие сообщения
     */
    ResponseWrapperMessage getAllSendMessages(int pageNumber, int pageSize, Long fromUserId);

    /**
     * Создать новое сообщение пользователю
     *
     * @param dto        параметры сообщения
     * @param fromUserId автор сообщения
     * @return отправленное сообщение
     */
    MessageResponse addMessage(MessageRequest dto, Long fromUserId);
}
