package com.github.alekseypetkun.socialmediaweb.repository;

import com.github.alekseypetkun.socialmediaweb.entity.Subscriber;
import com.github.alekseypetkun.socialmediaweb.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Репозиторий подписчиков
 */
@Repository
public interface SubscriberRepository extends JpaRepository<Subscriber, Long> {

    /**
     * Получить список подписок пользователя
     *
     * @param fromUserId пользователь
     * @return список подписок
     */
    List<Subscriber> findAllByFromUserId(User fromUserId);

    /**
     * Получить список подписчиков пользователя
     *
     * @param toUserId пользователь
     * @return список подписчиков
     */
    List<Subscriber> findAllByToUserId(User toUserId);
}
