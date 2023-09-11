package com.github.alekseypetkun.socialmediaweb.dto;

import com.github.alekseypetkun.socialmediaweb.constant.StatusSubscriber;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * DTO информация о подписчике.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class FullSubscriber {

    private Long userId; // id подписчика
    private String subscriberFirstName; // имя подписчика
    private String subscriberLastName; // фамилия подписчика
    private StatusSubscriber statusSubscriber; // статус подписчика
}
