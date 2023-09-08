package com.github.alekseypetkun.socialmediaweb.controller;

import com.github.alekseypetkun.socialmediaweb.security.jwt.JwtAuthentication;
import com.github.alekseypetkun.socialmediaweb.dto.LoginRequest;
import com.github.alekseypetkun.socialmediaweb.dto.LoginResponse;
import com.github.alekseypetkun.socialmediaweb.dto.RegisterRequest;
import com.github.alekseypetkun.socialmediaweb.dto.UserDto;
import com.github.alekseypetkun.socialmediaweb.service.AuthService;
import com.github.alekseypetkun.socialmediaweb.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * Контроллер по работе с авторизациями и регистрациями.
 */
@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
@Tag(name = "API по работе с авторизациями и регистрациями")
public class AuthController {

    private final AuthService authService;
    private final UserService userService;

    @PostMapping("/register")
    @Operation(
            summary = "Регистрация нового пользователя",
            description = "Нужно заполнить параметры для регистрации",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Пользователь зарегистрирован (OK)",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = RegisterRequest.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Пользователь не зарегистрирован, " +
                                    "т.к. не прошел валидацию (Bad Request)"
                    ),
                    @ApiResponse(
                            responseCode = "500",
                            description = "Пользователь не зарегистрирован, " +
                                    "т.к. такая почта уже существует (Internal Server Error)"
                    )
            }
    )
    public UserDto register(@RequestBody @Valid RegisterRequest dto) {

        return authService.registerUser(dto);
    }

    @PostMapping("/login")
    @Operation(
            summary = "Авторизация пользователя",
            description = "Нужно заполнить параметры для авторизации",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Пользователь авторизирован (OK)",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = LoginRequest.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "403",
                            description = "Пользователь не авторизирован, " +
                                    "т.к. ввел неправильные значения (Forbidden)"
                    )
            }
    )
    public LoginResponse login(@RequestBody @Valid LoginRequest dto) {

        return authService.login(dto);
    }

    @GetMapping("/info")
    @Operation(
            summary = "Получить информацию о пользователе",
            description = "Нужно ввести валидный токен",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Данные пользователя получены (OK)",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = UserDto.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "500",
                            description = "Данные пользователя не получены, " +
                                    "т.к. токен не валиден (Internal Server Error)"
                    )
            }
    )
    public UserDto getUserInfo() {

        return userService.getUserById(getAuthenticatedUserId());
    }

    private Long getAuthenticatedUserId() {
        JwtAuthentication authInfo = authService.getAuthInfo();
        return Long.valueOf(authInfo.getName());
    }
}
