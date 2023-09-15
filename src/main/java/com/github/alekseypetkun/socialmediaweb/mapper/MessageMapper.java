package com.github.alekseypetkun.socialmediaweb.mapper;

import com.github.alekseypetkun.socialmediaweb.dto.*;
import com.github.alekseypetkun.socialmediaweb.entity.Message;
import com.github.alekseypetkun.socialmediaweb.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

/**
 * Маппинг сущности сообщения
 */
@Mapper(componentModel = "spring")
public interface MessageMapper {

    /**
     * Преобразует сущность в дто
     *
     * @param entity сущность
     * @return дто
     */
    @Mapping(source = "id", target = "messageId")
    @Mapping(source = "status", target = "statusMessage")
    @Mapping(source = "message", target = "text")
    @Mapping(source = "toUserId.id", target = "toUser")
    @Mapping(source = "fromUserId.id", target = "fromUser")
    MessageResponse map(Message entity);

    /**
     * Преобразует дто в сущность
     *
     * @param dto дто
     * @return сущность
     */
    @Mapping(source = "toUserId", target = "toUserId.id")
    @Mapping(source = "text", target = "message")
    Message map(MessageRequest dto);


    /**
     * Преобразует сущность в дто
     *
     * @param entity сущность
     * @return дто
     */
    @Mapping(source = "firstName", target = "firstName")
    @Mapping(source = "lastName", target = "lastName")
    @Mapping(source = "id", target = "userId")
    FullUser mapToFullUser(User entity);
}
