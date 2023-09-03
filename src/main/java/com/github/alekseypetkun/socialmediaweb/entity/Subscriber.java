package com.github.alekseypetkun.socialmediaweb.entity;

import com.github.alekseypetkun.socialmediaweb.constant.StatusSubscriber;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Сущность подписка
 */
@Data
@Entity
@Builder(toBuilder = true) // Генерирует метод toBuilder(), который создает копию объекта класса и позволяет изменять значения полей копии объекта без изменения исходного объекта.
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "subscribers")
public class Subscriber {

    /**
     * Идентификатор подписки
     */
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * От кого заявка
     */
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
    @JoinColumn(name = "from_user_id")
    private User fromUserId;

    /**
     * Кому заявка
     */
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
    @JoinColumn(name = "to_user_id")
    private User toUserId;

    /**
     * Статус заявки
     */
    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private StatusSubscriber status;
}
