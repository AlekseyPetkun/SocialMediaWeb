package com.github.alekseypetkun.socialmediaweb.service;

import com.github.alekseypetkun.socialmediaweb.dto.*;
import com.github.alekseypetkun.socialmediaweb.entity.Post;
import org.springframework.web.multipart.MultipartFile;

/**
 * Сервис по работе с постами
 */
public interface PostService {

    /**
     * Создание поста по параметрам
     *
     * @param dto    параметры поста
     * @param image  картинка поста
     * @param userId идентификатор автора поста
     * @return созданный пост
     */
    PostDto addPost(CreatePost dto, MultipartFile image, Long userId);

    /**
     * Изменение поста по id
     *
     * @param postId идентификатор поста
     * @param dto    параметры поста
     * @param userId идентификатор автора
     * @return измененный пост
     */
    PostDto updatePostById(Long postId, UpdatePost dto, Long userId);

    /**
     * Удаление поста по id
     *
     * @param postId идентификатор поста
     * @param userId идентификатор автора
     */
    void deletePostById(Long postId, Long userId);

    /**
     * Показать весь список постов друзей и подписок
     *
     * @param pageNumber параметр начальной страницы
     * @param pageSize   параметр конечной страницы
     * @param userId     идентификатор авторизованного пользователя
     * @return список постов друзей и подписок
     */
    ResponseWrapperPosts getAllPostsBySubscriptions(int pageNumber, int pageSize, Long userId);

    /**
     * Получить информацию о посте по id
     *
     * @param postId идентификатор поста
     * @return информация о посте
     */
    FullPost getPostById(Long postId);

    /**
     * Получить посты авторизованного пользователя
     *
     * @param pageNumber параметр начальной страницы
     * @param pageSize   параметр конечной страницы
     * @param userId     идентификатор авторизованного пользователя
     * @return список постов автора
     */
    ResponseWrapperPosts getPostsMe(int pageNumber, int pageSize, Long userId);

    /**
     * Изменить картинку поста по ее идентификатору
     *
     * @param postId идентификатор поста
     * @param image  новая картинка поста
     * @param userId идентификатор авторизованного пользователя
     * @return true or false
     */
    boolean updatePostImage(Long postId, MultipartFile image, Long userId);

    /**
     * Поиск поста по его идентификатору
     *
     * @param postId идентификатор пота
     * @return найденный пост
     */
    Post findPostById(Long postId);

    /**
     * Получить все сохраненные посты
     *
     * @param pageNumber параметр начальной страницы
     * @param pageSize   параметр конечной страницы
     * @return список постов
     */
    ResponseWrapperPosts getAllPosts(int pageNumber, int pageSize);

    /**
     * Поиск постов по названию и/или по содержанию
     *
     * @param pageNumber параметр начальной страницы
     * @param pageSize   параметр конечной страницы
     * @param dto        название и/или содержание
     * @return список постов
     */
    ResponseWrapperPosts searchPosts(int pageNumber, int pageSize, SearchPost dto);

//    /**
     //     * Поиск постов по названию (заголовку)
     //     *
     //     * @param pageNumber параметр начальной страницы
     //     * @param pageSize   параметр конечной страницы
     //     * @param title      название поста (заголовок)
     //     * @return список постов
     //     */
//    ResponseWrapperPosts findByTitlePost(int pageNumber, int pageSize, String title);
//
//    /**
//     * Поиск постов по содержанию
//     *
//     * @param pageNumber параметр начальной страницы
//     * @param pageSize   параметр конечной страницы
//     * @param content    содержание поста
//     * @return список постов
//     */
//    ResponseWrapperPosts findByContentPost(int pageNumber, int pageSize, String content);
}
