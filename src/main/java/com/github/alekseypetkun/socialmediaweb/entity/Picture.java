package com.github.alekseypetkun.socialmediaweb.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Сущность картинка.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Picture {

    private String id; // идентификатор картинки
    private byte[] data; // картинка в байтах
}
