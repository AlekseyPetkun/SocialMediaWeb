package com.github.alekseypetkun.socialmediaweb.service;

import com.github.alekseypetkun.socialmediaweb.dto.ResponseWrapperSubscribers;
import com.github.alekseypetkun.socialmediaweb.dto.ResponseWrapperUsers;
import com.github.alekseypetkun.socialmediaweb.dto.UpdateUserDto;
import com.github.alekseypetkun.socialmediaweb.dto.UserDto;
import com.github.alekseypetkun.socialmediaweb.entity.User;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * Сервис по работе с пользователями
 */
public interface UserService {

    /**
     * Получить пользователя по id
     *
     * @param id идентификатор пользователя
     * @return найденный пользователь
     */
    UserDto getUserById(Long id);

    /**
     * Получить id аутентифицированного пользователя
     *
     * @return идентификатор аутентифицированного пользователя
     */
    Long getAuthenticatedUserId();

    /**
     * Поиск пользователя по его идентификатору
     *
     * @param userId идентификатор пользователя
     * @return найденный пользователь
     */
    User findUserById(Long userId);

    UserDto updateUser(UpdateUserDto dto, Long userId);

    ResponseWrapperSubscribers getAllSubscribers(int pageNumber, int pageSize, Long userId);

    ResponseWrapperUsers getAllUsers(int pageNumber, int pageSize);

    void makeFriendsById(Long fromUserId, Long toUserId);
    void unsubscribeById(Long fromUserId, Long toUserId);
    void subscribeById(Long fromUserId, Long toUserId);
}
