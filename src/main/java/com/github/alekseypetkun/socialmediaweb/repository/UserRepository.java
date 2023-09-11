package com.github.alekseypetkun.socialmediaweb.repository;

import com.github.alekseypetkun.socialmediaweb.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Репозиторий пользователей
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    /**
     * Поиск пользователя по его логину
     *
     * @param username логин пользователя
     * @return пользователь
     */
    User findByUsername(String username);
}
