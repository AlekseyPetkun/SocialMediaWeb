package com.github.alekseypetkun.socialmediaweb.repository;

import com.github.alekseypetkun.socialmediaweb.entity.Message;
import com.github.alekseypetkun.socialmediaweb.entity.Post;
import com.github.alekseypetkun.socialmediaweb.entity.User;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Репозиторий сообщений
 */
@Repository
public interface MessageRepository extends JpaRepository<Message, Long> {

    List<Message> findAllByToUserIdAndFromUserId(User toUserId, User FromUserId, Pageable pageable);
    List<Message> findAllByToUserId(User toUserId, Pageable pageable);
    List<Message> findAllByFromUserId(User fromUserId, Pageable pageable);
}
