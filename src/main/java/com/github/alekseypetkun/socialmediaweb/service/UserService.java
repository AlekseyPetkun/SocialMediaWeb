package com.github.alekseypetkun.socialmediaweb.service;

import com.github.alekseypetkun.socialmediaweb.dto.UserDto;
import com.github.alekseypetkun.socialmediaweb.entity.User;
import lombok.NonNull;

import java.util.Optional;

public interface UserService {

    User getByLogin(@NonNull String login);

    /**
     * Получить пользователя по id
     *
     * @param id идентификатор пользователя
     * @return найденный пользователь
     */
    UserDto getUserById(Long id);

    /**
     * Получить пользователя по его логину
     *
     * @param username логин пользователя
     * @return найденный пользователь
     */
    UserDto getUserByUsername(String username);
}
