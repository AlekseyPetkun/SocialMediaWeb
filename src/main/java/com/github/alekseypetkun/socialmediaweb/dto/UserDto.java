package com.github.alekseypetkun.socialmediaweb.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.github.alekseypetkun.socialmediaweb.constant.Role;
import lombok.*;

import java.time.LocalDateTime;

/**
 * DTO информация о пользователе
 */
@Data
@RequiredArgsConstructor
@AllArgsConstructor
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class UserDto {

    private Long id; // идентификатор пользователя
    private String username; // логин пользователя

    // поле будет записано только как часть десериализации,
    // но значение поля не включается в сериализацию
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password; // пароль
    private String email; // почта пользователя
    private Role role; // роль пользователя
    private String firstName; // имя пользователя
    private String lastName; // фамилия пользователя
    private boolean enabled; // доступ пользователя
    private LocalDateTime createdAt; // дата регистрации пользователя
    private LocalDateTime updatedAt; // дата изменения информации о пользователе
}
