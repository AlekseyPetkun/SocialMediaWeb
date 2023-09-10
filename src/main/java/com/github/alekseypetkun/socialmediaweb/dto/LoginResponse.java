package com.github.alekseypetkun.socialmediaweb.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.*;

import java.util.Date;

/**
 * DTO получение токенов
 */
@Getter
@Setter
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class LoginResponse {

    private Long userId; // идентификатор пользователя
    private String accessToken; // токен для доступа
    private String refreshToken; // токен для обновления токена для доступа
}
