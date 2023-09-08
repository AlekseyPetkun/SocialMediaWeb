package com.github.alekseypetkun.socialmediaweb.service.impl;


import com.github.alekseypetkun.socialmediaweb.dto.UserDto;
import com.github.alekseypetkun.socialmediaweb.entity.User;
import com.github.alekseypetkun.socialmediaweb.exception.NotFoundUserException;
import com.github.alekseypetkun.socialmediaweb.mapper.UserMapper;
import com.github.alekseypetkun.socialmediaweb.repository.UserRepository;
import com.github.alekseypetkun.socialmediaweb.service.UserService;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Override
    public User getByLogin(@NonNull String username) {

        return userRepository.findAll().stream()
                .filter(user -> username.equals(user.getUsername()))
                .findFirst()
                .orElseThrow(() -> new NotFoundUserException(username));
    }

    @Override
    public UserDto getUserById(Long id) {

        return userRepository.findById(id).map(userMapper::map)
                .orElseThrow(() -> new NotFoundUserException(id.toString()));
    }

    @Override
    public UserDto getUserByUsername(String username) {

        User user = userRepository.findByUsername(username);
        return userMapper.map(user);
    }
}
