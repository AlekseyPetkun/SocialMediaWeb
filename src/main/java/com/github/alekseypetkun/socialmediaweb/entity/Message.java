package com.github.alekseypetkun.socialmediaweb.entity;

import com.github.alekseypetkun.socialmediaweb.constant.StatusMessage;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * Сущность сообщения
 */
@Data
@Entity
@Builder(toBuilder = true)
// Генерирует метод toBuilder(), который создает копию объекта класса и позволяет изменять значения полей копии объекта без изменения исходного объекта.
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "messages")
public class Message {

    /**
     * Идентификатор сообщения
     */
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * От кого сообщение
     */
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
    @JoinColumn(name = "from_user_id")
    private User fromUserId;

    /**
     * Кому сообщение
     */
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
    @JoinColumn(name = "to_user_id")
    private User toUserId;

    /**
     * Текст сообщение
     */
    @Column(name = "message")
    private String message;

    /**
     * Дата и время сообщения
     */
    @Column(name = "date_time_message")
    private LocalDateTime dateTimeMessage;

    /**
     * Статус сообщения
     */
    @Column(name = "status", nullable = false)
    @Enumerated(EnumType.STRING)
    private StatusMessage status;
}
