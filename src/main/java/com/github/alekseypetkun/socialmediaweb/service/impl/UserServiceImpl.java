package com.github.alekseypetkun.socialmediaweb.service.impl;


import com.github.alekseypetkun.socialmediaweb.constant.StatusSubscriber;
import com.github.alekseypetkun.socialmediaweb.dto.*;
import com.github.alekseypetkun.socialmediaweb.entity.Subscriber;
import com.github.alekseypetkun.socialmediaweb.entity.User;
import com.github.alekseypetkun.socialmediaweb.exception.AuthorisationException;
import com.github.alekseypetkun.socialmediaweb.exception.NotFoundSubscriberException;
import com.github.alekseypetkun.socialmediaweb.exception.NotFoundUserException;
import com.github.alekseypetkun.socialmediaweb.mapper.SubscriberMapper;
import com.github.alekseypetkun.socialmediaweb.mapper.UserMapper;
import com.github.alekseypetkun.socialmediaweb.repository.SubscriberRepository;
import com.github.alekseypetkun.socialmediaweb.repository.UserRepository;
import com.github.alekseypetkun.socialmediaweb.security.jwt.JwtAuthentication;
import com.github.alekseypetkun.socialmediaweb.service.AuthService;
import com.github.alekseypetkun.socialmediaweb.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.TimeZone;

/**
 * Бизнес-логика по работе с пользователями
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final SubscriberRepository subscriberRepository;
    private final UserMapper userMapper;
    private final SubscriberMapper subscriberMapper;
    private final AuthService authService;

    TimeZone timeZone = TimeZone.getDefault();
    LocalDateTime localDateTime = LocalDateTime.now(timeZone.toZoneId());

    @Override
    public UserDto getUserById(Long id) {

        return userRepository.findById(id).map(userMapper::map)
                .orElseThrow(() -> new NotFoundUserException(id));
    }

    @Override
    public Long getAuthenticatedUserId() {

        JwtAuthentication authInfo = authService.getAuthInfo();
        return Long.valueOf(authInfo.getName());
    }

    @Override
    public User findUserById(Long userId) {

        return userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundUserException(userId));
    }

    @Override
    public UserDto updateUser(UpdateUserDto dto, Long userId) {

        User user = findUserById(userId);

        userMapper.patch(dto, user);

        user.setUpdatedAt(localDateTime);

        UserDto updatedUser = userMapper.map(userRepository.save(user));
        log.info("IN updateUser - user: {} updated", user);
        return updatedUser;
    }

    @Override
    public ResponseWrapperSubscribers getAllSubscribers(int pageNumber, int pageSize, Long userId) {

        User toUserId = findUserById(userId);

        List<Subscriber> friendsArrayList = subscriberRepository.findAllByToUserId(toUserId);

        List<FullSubscriber> dtoList = friendsArrayList.stream()
                .map(subscriberMapper::map)
                .toList();

        List<FullSubscriber> dtoResult = dtoList.stream()
                .skip(pageNumber)
                .limit(pageSize)
                .toList();

        return new ResponseWrapperSubscribers(dtoList.size(), dtoResult);
    }

    @Override
    public ResponseWrapperUsers getAllUsers(int pageNumber, int pageSize) {

        Pageable pageable = PageRequest
                .of(pageNumber, pageSize, Sort.by(Sort.Order.asc("id")));

        List<FullUser> dtoList = new ArrayList<>(userRepository
                .findAll(pageable).stream()
                .map(userMapper::mapToFullUser)
                .toList());

//        List<FullUser> dtoResult = dtoList.stream()
//                .skip(pageNumber)
//                .limit(pageSize)
//                .toList();

        return new ResponseWrapperUsers(dtoList.size(), dtoList);
    }

    @Override
    public void makeFriendsById(Long fromUserId, Long toUserId) {

        User fromUser = findUserById(fromUserId);
        User toUser = findUserById(toUserId);
        StatusSubscriber ss = StatusSubscriber.SUBSCRIPTION;

        Subscriber subscriber = subscriberRepository
                .findByFromUserIdAndToUserIdAndStatus(toUser, fromUser, ss);

        if (subscriber == null) {

            log.info("No subscribers found with from user ID {}, to user ID {}, and status {}", fromUser, toUser, ss);
            throw new NotFoundSubscriberException(toUserId);
        }

        subscriber.setStatus(StatusSubscriber.FRIENDS);
        subscriberRepository.save(subscriber);
        log.info("IN updateStatusSubscriber - subscriber: {} updated", subscriber);
    }

    @Override
    public void subscribeById(Long fromUserId, Long toUserId) {

        User fromUser = findUserById(fromUserId);
        User toUser = findUserById(toUserId);

        Subscriber subscriber1 = subscriberRepository
                .findByFromUserIdAndToUserId(toUser, fromUser);
        Subscriber subscriber2 = subscriberRepository
                .findByFromUserIdAndToUserId(fromUser, toUser);

        if (subscriber1 == null
                && subscriber2 == null) {

            Subscriber sb = new Subscriber();
            sb.setToUserId(toUser);
            sb.setFromUserId(fromUser);
            sb.setStatus(StatusSubscriber.SUBSCRIPTION);
            subscriberRepository.save(sb);
            log.info("IN createSubscriber - subscriber: {} create", sb);
        } else {
            log.info("No subscribers found with from user ID {}, to user ID {}", fromUser, toUser);
            throw new NotFoundSubscriberException(toUserId);
        }
    }

    @Override
    public void unsubscribeById(Long fromUserId, Long toUserId) {

        User fromUser = findUserById(fromUserId);
        User toUser = findUserById(toUserId);
        StatusSubscriber friends = StatusSubscriber.FRIENDS;
        StatusSubscriber subscription = StatusSubscriber.SUBSCRIPTION;

        Subscriber subscriber1 = subscriberRepository
                .findByFromUserIdAndToUserIdAndStatus(toUser, fromUser, friends);
        Subscriber subscriber2 = subscriberRepository
                .findByFromUserIdAndToUserIdAndStatus(fromUser, toUser, friends);

        Subscriber subscriber3 = subscriberRepository
                .findByFromUserIdAndToUserIdAndStatus(toUser, fromUser, subscription);
        Subscriber subscriber4 = subscriberRepository
                .findByFromUserIdAndToUserIdAndStatus(fromUser, toUser, subscription);

        if (subscriber1 == null
                && subscriber2 == null
                && subscriber3 == null
                && subscriber4 == null) {

            log.info("No subscribers found with from user ID {}, to user ID {}, and status {}", fromUser, toUser, friends);
            throw new NotFoundSubscriberException(toUserId);
        } else if (subscriber1 != null) {
            subscriber1.setStatus(StatusSubscriber.SUBSCRIPTION);
            subscriberRepository.save(subscriber1);
            log.info("IN updateStatusSubscriber - subscriber: {} updated", subscriber1);
        } else if (subscriber2 != null) {
            subscriber2.setStatus(StatusSubscriber.SUBSCRIPTION);
            subscriberRepository.save(subscriber2);
            log.info("IN updateStatusSubscriber - subscriber: {} updated", subscriber2);
        } else if (subscriber3 != null) {
            subscriberRepository.delete(subscriber3);
            log.info("IN deleteSubscriber - subscriber: {} deleted", subscriber3);
        } else {
            subscriberRepository.delete(subscriber4);
            log.info("IN deleteSubscriber - subscriber: {} deleted", subscriber4);
        }
    }
}
