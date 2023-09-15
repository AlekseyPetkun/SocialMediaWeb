package com.github.alekseypetkun.socialmediaweb.controller;

import com.github.alekseypetkun.socialmediaweb.dto.*;
import com.github.alekseypetkun.socialmediaweb.service.MessageService;
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
 * Контроллер по работе с сообщениями.
 */
@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/messages")
@Tag(name = "API по работе с сообщениями")
public class MessageController {

    private final UserService userService;
    private final MessageService messageService;

    @PostMapping
    @Operation(
            summary = "Добавить новое сообщение",
            description = "Нужно заполнить параметры для создания сообщения",
            responses = {
                    @ApiResponse(
                            responseCode = "201",
                            description = "Сообщение было отправлено (Created)",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = MessageResponse.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "401",
                            description = "Сообщение не отправлено, " +
                                    "т.к. пользователь не авторизован (Unauthorized)"
                    )
            }
    )
    public MessageResponse addMessage(@RequestBody @Valid MessageRequest dto) {

        Long userId = userService.getAuthenticatedUserId();
        return messageService.addMessage(dto, userId);
    }

    @GetMapping("/incoming/{fromUserId}")
    @Operation(
            summary = "Получить все входящие сообщения по id отправителя",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Информация успешно получена (Ok)",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = ResponseWrapperMessage.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "401",
                            description = "Информация не получена, " +
                                    "т.к. пользователь не авторизован (Unauthorized)"
                    )
            }
    )
    public ResponseWrapperMessage getAllIncomingMessagesById(
            @RequestParam(required = false, value = "page_number", defaultValue = "0") @Min(0) Integer pageNumber,
            @RequestParam(required = false, value = "page_size", defaultValue = "10") Integer pageSize,
            @PathVariable("fromUserId") Long fromUserId) {

        Long userId = userService.getAuthenticatedUserId();
        return messageService.getAllIncomingMessagesById(pageNumber, pageSize, userId, fromUserId);
    }

    @GetMapping("/send/{toUserId}")
    @Operation(
            summary = "Получить все исходящие сообщения по id получателя",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Информация успешно получена (Ok)",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = ResponseWrapperMessage.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "401",
                            description = "Информация не получена, " +
                                    "т.к. пользователь не авторизован (Unauthorized)"
                    )
            }
    )
    public ResponseWrapperMessage getAllSendMessagesById(
            @RequestParam(required = false, value = "page_number", defaultValue = "0") @Min(0) Integer pageNumber,
            @RequestParam(required = false, value = "page_size", defaultValue = "10") Integer pageSize,
            @PathVariable("toUserId") Long toUserId) {

        Long userId = userService.getAuthenticatedUserId();
        return messageService.getAllSendMessagesById(pageNumber, pageSize, userId, toUserId);
    }

    @GetMapping("/incoming")
    @Operation(
            summary = "Получить все входящие сообщения",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Информация успешно получена (Ok)",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = ResponseWrapperMessage.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "401",
                            description = "Информация не получена, " +
                                    "т.к. пользователь не авторизован (Unauthorized)"
                    )
            }
    )
    public ResponseWrapperMessage getAllIncomingMessages(
            @RequestParam(required = false, value = "page_number", defaultValue = "0") @Min(0) Integer pageNumber,
            @RequestParam(required = false, value = "page_size", defaultValue = "10") Integer pageSize) {

        Long userId = userService.getAuthenticatedUserId();
        return messageService.getAllIncomingMessages(pageNumber, pageSize, userId);
    }

    @GetMapping("/send")
    @Operation(
            summary = "Получить все исходящие сообщения",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Информация успешно получена (Ok)",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = ResponseWrapperMessage.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "401",
                            description = "Информация не получена, " +
                                    "т.к. пользователь не авторизован (Unauthorized)"
                    )
            }
    )
    public ResponseWrapperMessage getAllSendMessages(
            @RequestParam(required = false, value = "page_number", defaultValue = "0") @Min(0) Integer pageNumber,
            @RequestParam(required = false, value = "page_size", defaultValue = "10") Integer pageSize) {

        Long userId = userService.getAuthenticatedUserId();
        return messageService.getAllSendMessages(pageNumber, pageSize, userId);
    }
}
