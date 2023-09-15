package com.github.alekseypetkun.socialmediaweb.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.github.alekseypetkun.socialmediaweb.constant.StatusMessage;
import lombok.*;

import java.time.LocalDateTime;

/**
 * DTO получить сообщение
 */
@Getter
@Setter
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class MessageResponse {

    private Long messageId; // идентификатор сообщения
    private Long toUser; // информация о получателе
    private Long fromUser; // информация об отправителе
    private String text; // текст сообщения
    private LocalDateTime dateTimeMessage; // дата создания сообщения
    private StatusMessage statusMessage; // статус сообщения
}
