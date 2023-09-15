package com.github.alekseypetkun.socialmediaweb.repository;

import com.github.alekseypetkun.socialmediaweb.entity.Post;
import com.github.alekseypetkun.socialmediaweb.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Репозиторий постов
 */
@Repository
public interface PostRepository extends JpaRepository<Post, Long> {

    /**
     * Получить список постов автора
     *
     * @param author автор
     * @return список постов
     */
    List<Post> findAllByAuthor(User author);

    /**
     * Вывод всех постов по названию
     *
     * @param title название постов
     * @return список постов
     */
    List<Post> findAllByTitleContainingIgnoreCase(String title);
}
