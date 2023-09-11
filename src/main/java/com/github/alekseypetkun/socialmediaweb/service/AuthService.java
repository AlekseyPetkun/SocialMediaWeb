package com.github.alekseypetkun.socialmediaweb.service;


import com.github.alekseypetkun.socialmediaweb.entity.Post;
import com.github.alekseypetkun.socialmediaweb.entity.User;
import com.github.alekseypetkun.socialmediaweb.security.jwt.JwtAuthentication;
import com.github.alekseypetkun.socialmediaweb.dto.LoginRequest;
import com.github.alekseypetkun.socialmediaweb.dto.LoginResponse;
import com.github.alekseypetkun.socialmediaweb.dto.RegisterRequest;
import com.github.alekseypetkun.socialmediaweb.dto.UserDto;
import com.github.alekseypetkun.socialmediaweb.exception.AuthenticationException;

/**
 * Сервис по работе с аутентификацией и регистрацией.
 */
public interface AuthService {

    UserDto registerUser(RegisterRequest dto);

    LoginResponse login(LoginRequest authRequest) throws AuthenticationException;

    LoginResponse getAccessToken(String refreshToken);

    LoginResponse getNewRefreshToken(String refreshToken) throws AuthenticationException;

    JwtAuthentication getAuthInfo();

    boolean checkAccess(Post post, User user);

    boolean checkAccess(User user);

//    LoginResponse authenticate(String username, String password);
//
//    /**
//     * Выдача валидного токена аутентифицированного пользователя
//     *
//     * @param username логин пользователя
//     * @param password пароль пользователя
//     * @return валидный токен пользователя
//     */
//    LoginResponse checkToken(String username, String password);
//
////    LoginResponse getAccessToken(@NonNull String refreshToken);
////    LoginResponse refresh(@NonNull String refreshToken);
////    JwtAuthentication getAuthInfo();


}
