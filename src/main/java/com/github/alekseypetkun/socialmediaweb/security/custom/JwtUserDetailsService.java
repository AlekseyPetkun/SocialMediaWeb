package com.github.alekseypetkun.socialmediaweb.security.custom;

import com.github.alekseypetkun.socialmediaweb.entity.User;
import com.github.alekseypetkun.socialmediaweb.exception.NotFoundUserException;
import com.github.alekseypetkun.socialmediaweb.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class JwtUserDetailsService implements CustomUserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) {

        User user = userRepository.findByUsername(username);

        if (user == null) {
            throw new NotFoundUserException("User with username: " + username + " not found");
        }

        JwtUser jwtUser = new JwtUser(user);
        log.info("IN loadUserByUsername - user with username: {} successfully loaded", username);
        return jwtUser;
    }

    @Override
    public UserDetails loadUserById(Long userId) {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundUserException("User with userId: " + userId + " not found"));

        JwtUser jwtUser = new JwtUser(user);
        log.info("IN loadUserByUsername - user with userId: {} successfully loaded", userId);
        return jwtUser;
    }
}
