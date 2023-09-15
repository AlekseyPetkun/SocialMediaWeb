package com.github.alekseypetkun.socialmediaweb.mapper;

import com.github.alekseypetkun.socialmediaweb.dto.*;
import com.github.alekseypetkun.socialmediaweb.entity.Post;
import com.github.alekseypetkun.socialmediaweb.entity.Subscriber;
import org.mapstruct.*;

/**
 * Маппинг сущности подписки
 */
@Mapper(componentModel = "spring")
public interface SubscriberMapper {

    /**
     * Преобразует сущность в дто
     *
     * @param entity сущность
     * @return дто
     */
    @Mapping(source = "fromUserId.id", target = "userId")
    @Mapping(source = "fromUserId.firstName", target = "subscriberFirstName")
    @Mapping(source = "fromUserId.lastName", target = "subscriberLastName")
    @Mapping(source = "status", target = "statusSubscriber")
    FullSubscriber map(Subscriber entity);

    /**
     * Преобразует дто в сущность
     *
     * @param dto дто
     * @return сущность
     */
    @InheritInverseConfiguration
    Subscriber map(FullSubscriber dto);
}
