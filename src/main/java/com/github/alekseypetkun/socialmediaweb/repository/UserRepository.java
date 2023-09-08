package com.github.alekseypetkun.socialmediaweb.repository;

import com.github.alekseypetkun.socialmediaweb.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    User findByUsername(String username);
}
