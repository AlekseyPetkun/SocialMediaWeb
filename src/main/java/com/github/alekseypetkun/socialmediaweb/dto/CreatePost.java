package com.github.alekseypetkun.socialmediaweb.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * ДТО создание поста
 */
@Data
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class CreatePost {

    @NotEmpty(message = "строка с заголовком не может быть пустой!")
    private String title; // заголовок поста
    @NotEmpty(message = "строка с текстом не может быть пустой!")
    private String content; // содержание поста
}
