package com.github.alekseypetkun.socialmediaweb.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.*;

import java.time.LocalDateTime;

/**
 * ДТО поста
 */
@Data
@RequiredArgsConstructor
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class PostDto {

    private Long id; // идентификатор поста
    private String title; // заголовок поста
    private String content; // содержание поста
    private String image; // ссылка на картинку
    private LocalDateTime dateTimePost; // дата создания поста
    private Long authorPost; // идентификатор автора поста
}
