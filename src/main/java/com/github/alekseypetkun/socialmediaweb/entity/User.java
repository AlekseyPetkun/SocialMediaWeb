package com.github.alekseypetkun.socialmediaweb.entity;

import com.github.alekseypetkun.socialmediaweb.constant.Role;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.Collection;

/**
 * Сущность пользователь
 */
@Entity
@Data
@Builder(toBuilder = true)
// Генерирует метод toBuilder(), который создает копию объекта класса и позволяет изменять значения полей копии объекта без изменения исходного объекта.
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "users")
public class User {

    /**
     * Идентификатор пользователя
     */
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Логин пользователя
     */
    @Column(name = "username")
    private String username;

    /**
     * Пароль пользователя
     */
    @Column(name = "password")
    private String password;

    /**
     * Почта пользователя
     */
    @Column(name = "email")
    private String email;

    /**
     * Роль пользователя для авторизации
     */
    @Column(name = "user_role", nullable = false)
    @Enumerated(EnumType.STRING)
    private Role role;

    /**
     * Имя пользователя
     */
    @Column(name = "first_name")
    private String firstName;

    /**
     * Фамилия пользователя
     */
    @Column(name = "last_name")
    private String lastName;

    /**
     * Активация/деактивация пользователя
     */
    @Column(name = "enabled")
    private boolean enabled;

    /**
     * Дата и время создания пользователя
     */
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    /**
     * Дата и время изменения пользователя
     */
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    /**
     * Список постов пользователя
     */
    @ToString.Exclude
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "author",
            cascade = CascadeType.ALL, orphanRemoval = true)
    private Collection<Post> posts;

    /**
     * Список подписчиков пользователя
     */
    @ToString.Exclude
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "toUserId",
            cascade = CascadeType.ALL, orphanRemoval = true)
    private Collection<Subscriber> subscribers;

    /**
     * Список сообщений пользователя
     */
    @ToString.Exclude
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "toUserId",
            cascade = CascadeType.ALL, orphanRemoval = true)
    private Collection<Message> messages;

    @ToString.Include(name = "password") // Строковое представление этого поля будет засекречено
    private String maskPassword() {
        return "*****";
    }


}
