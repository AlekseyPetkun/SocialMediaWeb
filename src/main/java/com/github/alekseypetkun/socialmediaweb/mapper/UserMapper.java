package com.github.alekseypetkun.socialmediaweb.mapper;

import com.github.alekseypetkun.socialmediaweb.dto.RegisterRequest;
import com.github.alekseypetkun.socialmediaweb.dto.UpdatePost;
import com.github.alekseypetkun.socialmediaweb.dto.UpdateUserDto;
import com.github.alekseypetkun.socialmediaweb.dto.UserDto;
import com.github.alekseypetkun.socialmediaweb.entity.Post;
import com.github.alekseypetkun.socialmediaweb.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

/**
 * Маппинг сущности пользователя
 */
@Mapper(componentModel = "spring")
public interface UserMapper {

    /**
     * Преобразует сущность в дто
     *
     * @param entity сущность
     * @return дто
     */
    UserDto map(User entity);

    /**
     * Преобразует дто в сущность
     *
     * @param dto дто
     * @return сущность
     */
    User map(RegisterRequest dto);

    /**
     * Преобразует дто в сущность не изменяя поля на null
     *
     * @param dto    ДТО
     * @param entity сущность
     */
    @Mapping(source = "username", target = "username",
            nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(source = "email", target = "email",
            nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(source = "firstName", target = "firstName",
            nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(source = "lastName", target = "lastName",
            nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void patch(UpdateUserDto dto, @MappingTarget User entity);
}
