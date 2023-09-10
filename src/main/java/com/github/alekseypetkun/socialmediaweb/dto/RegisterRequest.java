package com.github.alekseypetkun.socialmediaweb.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO регистрация
 */
@Data
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class RegisterRequest {

    @NotEmpty(message = "строка с именем не может быть пустой!")
    private String firstName; // имя
    @NotEmpty(message = "строка с фамилией не может быть пустой!")
    private String lastName; // фамилия
    @NotEmpty(message = "строка с email не может быть пустой!")
    private String email; // email
    @NotEmpty(message = "строка с паролем не может быть пустой!")
    private String password; // пароль
}
