package com.github.alekseypetkun.socialmediaweb.service;

import org.springframework.web.multipart.MultipartFile;

/**
 * Сервис по работе с картинками.
 */
public interface PictureService {

    /**
     * Загружаем новое изображение
     *
     * @param image новое изображение
     * @return название файла
     */
    String addImage(MultipartFile image);

    /**
     * Загружает изображение поста по идентификатору изображения
     *
     * @param fileName идентификатору изображения
     * @return изображение в виде byte[]
     */
    byte[] loadImagePost(String fileName);
}
