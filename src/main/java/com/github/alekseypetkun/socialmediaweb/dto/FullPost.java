package com.github.alekseypetkun.socialmediaweb.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * DTO информация об посте.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class FullPost {

    private Long postId; // id поста
    private String authorFirstName; // имя автора поста
    private String authorLastName; // фамилия автора поста
    private String title; // название поста
    private String content; //содержание поста
    private String image; // картинка поста
    private LocalDateTime dateTimePost; // дата создания поста
}
