package com.github.alekseypetkun.socialmediaweb.controller;

import com.github.alekseypetkun.socialmediaweb.dto.*;
import com.github.alekseypetkun.socialmediaweb.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * Контроллер для работы с пользователями
 */
@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
@Tag(name = "API по работе с пользователями")
public class UserController {

    private final UserService userService;

    @PatchMapping("/me")
    @Operation(
            summary = "Обновить информацию о пользователе",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Информация успешно обновилась (Ok)",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = UserDto.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "401",
                            description = "Информация не обновилась, " +
                                    "т.к. пользователь не авторизован (Unauthorized)"
                    ),
                    @ApiResponse(
                            responseCode = "403",
                            description = "Информация не обновилась, " +
                                    "т.к. у пользователя нет прав на это (Forbidden)"
                    )
            }
    )
    public UserDto updateUser(@RequestBody @Valid UpdateUserDto dto) {

        Long userId = userService.getAuthenticatedUserId();
        return userService.updateUser(dto, userId);
    }

    @GetMapping("/subscriber")
    @Operation(
            summary = "Получить всех подписчиков пользователя",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Получен список подписчиков (Ok)",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = ResponseWrapperSubscribers.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Список подписчиков не получен (Not Found)"
                    )
            }
    )
    public ResponseWrapperSubscribers getAllSubscribers(
            @RequestParam(required = false, value = "page_number", defaultValue = "0") @Min(0) Integer pageNumber,
            @RequestParam(required = false, value = "page_size", defaultValue = "10") Integer pageSize) {

        Long userId = userService.getAuthenticatedUserId();
        return userService.getAllSubscribers(pageNumber, pageSize, userId);
    }

    @GetMapping()
    @Operation(
            summary = "Получить всех пользователей",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Получен список пользователей (Ok)",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = ResponseWrapperUsers.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Список пользователей не получен (Not Found)"
                    )
            }
    )
    public ResponseWrapperUsers getAllUsers(
            @RequestParam(required = false, value = "page_number", defaultValue = "0") @Min(0) Integer pageNumber,
            @RequestParam(required = false, value = "page_size", defaultValue = "10") Integer pageSize) {

        return userService.getAllUsers(pageNumber, pageSize);
    }

    @PostMapping("/make_friends/{toUserId}")
    @Operation(
            summary = "Принять заявку в друзья по id подписчика",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Заявка принята (OK)"
                    ),
                    @ApiResponse(
                            responseCode = "401",
                            description = "Заявка не принята, " +
                                    "т.к. пользователь не авторизован (Unauthorized)"
                    )
            }
    )
    public void makeFriendsById(@PathVariable("toUserId") Long toUserId) {

        Long userId = userService.getAuthenticatedUserId();
        userService.makeFriendsById(userId, toUserId);
    }

    @PostMapping("/subscribe/{toUserId}")
    @Operation(
            summary = "Подписаться на пользователя по его id",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Заявка отправлена (OK)"
                    ),
                    @ApiResponse(
                            responseCode = "401",
                            description = "Заявка не отправлена, " +
                                    "т.к. пользователь не авторизован (Unauthorized)"
                    )
            }
    )
    public void subscribeById(@PathVariable("toUserId") Long toUserId) {

        Long userId = userService.getAuthenticatedUserId();
        userService.subscribeById(userId, toUserId);
    }

    @PostMapping("/unsubscribe/{toUserId}")
    @Operation(
            summary = "Удалить пользователя из друзей по его id",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Пользователь удален из друзей (OK)"
                    ),
                    @ApiResponse(
                            responseCode = "401",
                            description = "Пользовательне  удален из друзей, " +
                                    "т.к. пользователь не авторизован (Unauthorized)"
                    )
            }
    )
    public void unsubscribeById(@PathVariable("toUserId") Long toUserId) {

        Long userId = userService.getAuthenticatedUserId();
        userService.unsubscribeById(userId, toUserId);
    }
}
