package com.github.alekseypetkun.socialmediaweb.service.impl;

import com.github.alekseypetkun.socialmediaweb.constant.Role;
import com.github.alekseypetkun.socialmediaweb.security.jwt.JwtAuthentication;
import com.github.alekseypetkun.socialmediaweb.dto.LoginRequest;
import com.github.alekseypetkun.socialmediaweb.dto.LoginResponse;
import com.github.alekseypetkun.socialmediaweb.dto.RegisterRequest;
import com.github.alekseypetkun.socialmediaweb.dto.UserDto;
import com.github.alekseypetkun.socialmediaweb.entity.User;
import com.github.alekseypetkun.socialmediaweb.exception.AuthException;
import com.github.alekseypetkun.socialmediaweb.mapper.UserMapper;
import com.github.alekseypetkun.socialmediaweb.repository.UserRepository;
import com.github.alekseypetkun.socialmediaweb.security.jwt.JwtProvider;
import com.github.alekseypetkun.socialmediaweb.security.PBFDK2PasswordEncoder;
import com.github.alekseypetkun.socialmediaweb.security.custom.CustomUserDetails;
import com.github.alekseypetkun.socialmediaweb.security.custom.CustomUserDetailsService;
import com.github.alekseypetkun.socialmediaweb.service.AuthService;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.TimeZone;

/**
 * Бизнес-логика по регистрации и аутентификации
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final CustomUserDetailsService customUserDetailsService;
    private final PBFDK2PasswordEncoder passwordEncoder;

    private final Map<String, String> refreshTokenStorage = new HashMap<>();
    private final JwtProvider jwtProvider;
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    TimeZone timeZone = TimeZone.getDefault();
    LocalDateTime localDateTime = LocalDateTime.now(timeZone.toZoneId());

    @Override
    public UserDto registerUser(RegisterRequest dto) {

        User user = userMapper.map(dto);

        userRepository.save(user.toBuilder()
                .password(passwordEncoder.encode(user.getPassword()))
                .username(dto.getEmail())
                .role(Role.USER)
                .enabled(true)
                .createdAt(localDateTime)
                .updatedAt(localDateTime)
                .build());

        log.info("IN registerUser - user: {} created", userMapper.map(user));
        return userMapper.map(user);
    }

    @Override
    public LoginResponse login(LoginRequest authRequest) throws AuthException {

        return checkToken(authRequest.getUsername(),
                authRequest.getPassword());
    }

    /**
     * Выдача валидного токена аутентифицированного пользователя
     *
     * @param username логин пользователя
     * @param password пароль пользователя
     * @return валидный токен пользователя
     */
    private LoginResponse checkToken(String username, String password) {

        final CustomUserDetails user = (CustomUserDetails) customUserDetailsService
                .loadUserByUsername(username);

        if (user == null) {
            throw new AuthException("Invalid username", "8082_INVALID_USERNAME");
        }

        if (!user.isEnabled()) {
            throw new AuthException("Account disabled", "8082_USER_ACCOUNT_DISABLED");
        }

        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new AuthException("Invalid password", "8082_INVALID_PASSWORD");
        }

        final String accessToken = jwtProvider.generateAccessToken(user);
        final String refreshToken = jwtProvider.generateRefreshToken(user);

        refreshTokenStorage.put(String.valueOf(user.getUserById()), refreshToken);

        return LoginResponse.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .userId(user.getUserById())
                .build();
    }

    @Override
    public LoginResponse getAccessToken(String refreshToken) {

        final Claims claims = jwtProvider.getRefreshClaims(refreshToken);
        final String userId = claims.getSubject();
        final String saveRefreshToken = refreshTokenStorage.get(userId);
        final CustomUserDetails user = (CustomUserDetails) customUserDetailsService
                .loadUserById(Long.valueOf(userId));

        if (jwtProvider.validateRefreshToken(refreshToken)) {

            if (saveRefreshToken != null && saveRefreshToken.equals(refreshToken)) {

                final String accessToken = jwtProvider.generateAccessToken(user);

                return new LoginResponse(user.getUserById(), accessToken, null);
            }
        }
        return new LoginResponse(user.getUserById(), null, null);
    }

    @Override
    public LoginResponse getNewRefreshToken(String refreshToken) throws AuthException {

        if (jwtProvider.validateRefreshToken(refreshToken)) {

            final Claims claims = jwtProvider.getRefreshClaims(refreshToken);
            final String userId = claims.getSubject();
            final String saveRefreshToken = refreshTokenStorage.get(userId);

            if (saveRefreshToken != null && saveRefreshToken.equals(refreshToken)) {

                final CustomUserDetails user = (CustomUserDetails) customUserDetailsService.loadUserById(Long.valueOf(userId));
                final String accessToken = jwtProvider.generateAccessToken(user);
                final String newRefreshToken = jwtProvider.generateRefreshToken(user);

                refreshTokenStorage.put(String.valueOf(user.getUserById()), newRefreshToken);

                return new LoginResponse(user.getUserById(), accessToken, newRefreshToken);
            }
        }
        throw new AuthException("Invalid jwt token", "8082_INVALID_JWT_TOKEN");
    }

    @Override
    public JwtAuthentication getAuthInfo() {

        return (JwtAuthentication) SecurityContextHolder.getContext().getAuthentication();
    }
}
