package com.github.alekseypetkun.socialmediaweb.service.impl;

import com.github.alekseypetkun.socialmediaweb.constant.StatusMessage;
import com.github.alekseypetkun.socialmediaweb.dto.*;
import com.github.alekseypetkun.socialmediaweb.entity.Message;
import com.github.alekseypetkun.socialmediaweb.entity.User;
import com.github.alekseypetkun.socialmediaweb.exception.NotFoundIncomingMessageException;
import com.github.alekseypetkun.socialmediaweb.exception.NotFoundSendMessageException;
import com.github.alekseypetkun.socialmediaweb.mapper.MessageMapper;
import com.github.alekseypetkun.socialmediaweb.repository.MessageRepository;
import com.github.alekseypetkun.socialmediaweb.service.MessageService;
import com.github.alekseypetkun.socialmediaweb.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.TimeZone;

/**
 * Бизнес-логика по работе с сообщениями
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class MessageServiceImpl implements MessageService {

    private final UserService userService;
    private final MessageMapper messageMapper;
    private final MessageRepository messageRepository;

    @Override
    public ResponseWrapperMessage getAllIncomingMessagesById(int pageNumber, int pageSize, Long toUserId, Long fromUserId) {

        User fromUser = userService.findUserById(fromUserId);
        User toUser = userService.findUserById(toUserId);

        Pageable pageable = PageRequest
                .of(pageNumber, pageSize, Sort.by(Sort.Order.desc("dateTimeMessage")));

        List<Message> messages = messageRepository.findAllByToUserIdAndFromUserId(toUser, fromUser, pageable);

        if (messages == null) {
            throw new NotFoundIncomingMessageException(fromUserId);
        }

        messages.forEach(value -> {
            if (value.getStatus().equals(StatusMessage.UNREAD)) {
                value.setStatus(StatusMessage.READ);
            }
        });

        List<MessageResponse> dtoList = messages.stream()
                .map(messageMapper::map)
                .toList();

        return new ResponseWrapperMessage(dtoList.size(), dtoList);
    }

    @Override
    public ResponseWrapperMessage getAllSendMessagesById(int pageNumber, int pageSize, Long fromUserId, Long toUserId) {

        User fromUser = userService.findUserById(fromUserId);
        User toUser = userService.findUserById(toUserId);

        Pageable pageable = PageRequest
                .of(pageNumber, pageSize, Sort.by(Sort.Order.desc("dateTimeMessage")));

        List<Message> messages = messageRepository.findAllByToUserIdAndFromUserId(toUser, fromUser, pageable);

        if (messages == null) {
            throw new NotFoundSendMessageException(fromUserId);
        }

        List<MessageResponse> dtoList = messages.stream()
                .map(messageMapper::map)
                .toList();

        return new ResponseWrapperMessage(dtoList.size(), dtoList);
    }

    @Override
    public ResponseWrapperMessage getAllIncomingMessages(int pageNumber, int pageSize, Long toUserId) {

        User toUser = userService.findUserById(toUserId);

        Pageable pageable = PageRequest
                .of(pageNumber, pageSize, Sort.by(Sort.Order.desc("dateTimeMessage")));

        List<Message> messages = messageRepository.findAllByToUserId(toUser, pageable);

        if (messages == null) {
            throw new NotFoundIncomingMessageException();
        }

        messages.forEach(value -> {
            if (value.getStatus().equals(StatusMessage.UNREAD)) {
                value.setStatus(StatusMessage.READ);
            }
        });

        List<MessageResponse> dtoList = messages.stream()
                .map(messageMapper::map)
                .toList();

        return new ResponseWrapperMessage(dtoList.size(), dtoList);
    }

    @Override
    public ResponseWrapperMessage getAllSendMessages(int pageNumber, int pageSize, Long fromUserId) {

        User fromUser = userService.findUserById(fromUserId);

        Pageable pageable = PageRequest
                .of(pageNumber, pageSize, Sort.by(Sort.Order.desc("dateTimeMessage")));

        List<Message> messages = messageRepository.findAllByFromUserId(fromUser, pageable);

        if (messages == null) {
            throw new NotFoundSendMessageException();
        }

        List<MessageResponse> dtoList = messages.stream()
                .map(messageMapper::map)
                .toList();

        return new ResponseWrapperMessage(dtoList.size(), dtoList);
    }

    @Override
    public MessageResponse addMessage(MessageRequest dto, Long fromUserId) {

        TimeZone timeZone = TimeZone.getDefault();
        LocalDateTime dateTimeNow = LocalDateTime.now(timeZone.toZoneId());

        User fromUser = userService.findUserById(fromUserId);

        Message message = messageMapper.map(dto);

        message.setDateTimeMessage(dateTimeNow);
        message.setStatus(StatusMessage.UNREAD);
        message.setFromUserId(fromUser);

        MessageResponse newMessage = messageMapper.map(messageRepository.save(message));
        log.info("IN addMessage - message: {} created", message);
        return newMessage;
    }
}
