package com.github.alekseypetkun.socialmediaweb.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO создать сообщение
 */
@Data
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class MessageRequest {

    @NotEmpty(message = "строка с текстом не может быть пустой!")
    private String text; // текст сообщения
    @NotNull(message = "строка с id не может быть пустой!")
    private Long toUserId; // идентификатор получателя
}
