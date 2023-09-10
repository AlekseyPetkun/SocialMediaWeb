package com.github.alekseypetkun.socialmediaweb.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

/**
 * Сущность пост
 */
@Entity
@Data
@Builder(toBuilder = true)
// Генерирует метод toBuilder(), который создает копию объекта класса и позволяет изменять значения полей копии объекта без изменения исходного объекта.
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "posts")
public class Post {

    /**
     * Идентификатор поста
     */
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Заголовок поста
     */
    @Column(name = "title")
    private String title;

    /**
     * Текст поста
     */
    @Column(name = "content")
    private String content;

    /**
     * Картинка поста
     */
    @Column(name = "image")
    private String image;

    /**
     * Дата и время поста
     */
    @Column(name = "date_time_post")
    private LocalDateTime dateTimePost;

    /**
     * Автор поста
     */
    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
    @JoinColumn(name = "author_id")
//    @ToString.Exclude
    private User author;
}
