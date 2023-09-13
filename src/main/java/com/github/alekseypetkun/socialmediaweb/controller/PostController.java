package com.github.alekseypetkun.socialmediaweb.controller;

import com.github.alekseypetkun.socialmediaweb.dto.*;
import com.github.alekseypetkun.socialmediaweb.service.PictureService;
import com.github.alekseypetkun.socialmediaweb.service.PostService;
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
import org.springframework.web.multipart.MultipartFile;

/**
 * Контроллер по работе с постами.
 */
@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/posts")
@Tag(name = "API по работе с постами")
public class PostController {

    private final UserService userService;
    private final PostService postService;
    private final PictureService pictureService;

    @PostMapping(consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    @Operation(
            summary = "Добавить новый пост",
            description = "Нужно заполнить параметры для создания поста",
            responses = {
                    @ApiResponse(
                            responseCode = "201",
                            description = "Пост был добавлен (Created)",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = PostDto.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "401",
                            description = "Пост не добавлена, " +
                                    "т.к. пользователь не авторизован (Unauthorized)"
                    )
            }
    )
    public PostDto addAd(@RequestPart("dto") @Valid CreatePost dto,
                         @RequestPart(name = "image") MultipartFile image) {

        Long userId = userService.getAuthenticatedUserId();
        return postService.addPost(dto, image, userId);
    }

    @PatchMapping("/{postId}")
    @Operation(
            summary = "Обновить информацию о посте",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Информация успешно обновилась (Ok)",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = PostDto.class)
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
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Информация не обновилась, " +
                                    "т.к. пост не найден (Not Found)"
                    )
            }
    )
    public PostDto updatePosts(@PathVariable("postId") Long postId,
                               @RequestBody UpdatePost dto) {

        Long userId = userService.getAuthenticatedUserId();
        return postService.updatePostById(postId, dto, userId);
    }

    @GetMapping("/{postId}")
    @Operation(
            summary = "Получить информацию о посте",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Информация успешно получена (Ok)",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = FullPost.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "401",
                            description = "Информация не получена, " +
                                    "т.к. пользователь не авторизован (Unauthorized)"
                    )
            }
    )
    public FullPost getPost(@PathVariable("postId") Long postId) {

        return postService.getPostById(postId);
    }

    @DeleteMapping("/{postId}")
    @Operation(
            summary = "Удалить пост",
            responses = {
                    @ApiResponse(
                            responseCode = "204",
                            description = "Пост удален (No Content)"
                    ),
                    @ApiResponse(
                            responseCode = "401",
                            description = "Пост не удален, " +
                                    "т.к. пользователь не авторизован (Unauthorized)"
                    ),
                    @ApiResponse(
                            responseCode = "403",
                            description = "Пост не удален, " +
                                    "т.к. у пользователя нет прав на это (Forbidden)"
                    )
            }
    )
    public void deletePost(@PathVariable("postId") Long postId) {

        Long userId = userService.getAuthenticatedUserId();
        postService.deletePostById(postId, userId);
    }

    @GetMapping("/subscriptions")
    @Operation(
            summary = "Получить все посты друзей и подписок",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Получен список постов друзей и подписок (Ok)",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = ResponseWrapperPosts.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Список постов друзей не получен (Not Found)"
                    )
            }
    )
    public ResponseWrapperPosts getAllFeeds(
            @RequestParam(required = false, value = "page_number", defaultValue = "0") @Min(0) Integer pageNumber,
            @RequestParam(required = false, value = "page_size", defaultValue = "10") Integer pageSize) {

        Long userId = userService.getAuthenticatedUserId();
        return postService.getAllPostsBySubscriptions(pageNumber, pageSize, userId);
    }

    @GetMapping("/me")
    @Operation(
            summary = "Получить посты авторизованного пользователя",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Получен список постов (Ok)",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = ResponseWrapperPosts.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "401",
                            description = "Список постов не получен, " +
                                    "т.к. пользователь не авторизован (Unauthorized)"
                    )
            }
    )
    public ResponseWrapperPosts getPostsMe(
            @RequestParam(required = false, value = "page_number", defaultValue = "0") @Min(0) Integer pageNumber,
            @RequestParam(required = false, value = "page_size", defaultValue = "10") Integer pageSize) {

        Long userId = userService.getAuthenticatedUserId();
        return postService.getPostsMe(pageNumber, pageSize, userId);
    }

    @PatchMapping(value = "/{postId}/image", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    @Operation(
            summary = "Обновить картинку поста",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Картинка успешно обновилась (OK)",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_OCTET_STREAM_VALUE,
                                    schema = @Schema(implementation = String[].class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "401",
                            description = "Картинка не обновилась, " +
                                    "т.к. пользователь не авторизован (Unauthorized)"
                    ),
                    @ApiResponse(
                            responseCode = "403",
                            description = "Картинка не обновилась, " +
                                    "т.к. у пользователя нет прав на это (Forbidden)"
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Картинка не обновилась, " +
                                    "т.к. пост не найден (Not Found)"
                    )
            }
    )
    public boolean updatePostImage(@PathVariable("postId") Long postId,
                                   @RequestPart(name = "image") MultipartFile image) {

        Long userId = userService.getAuthenticatedUserId();
        return postService.updatePostImage(postId, image, userId);
    }

    @GetMapping
    @Operation(
            summary = "Получить все сохраненные посты",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Получен список постов (Ok)",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = ResponseWrapperPosts.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Список постов не получен (Not Found)"
                    )
            }
    )
    public ResponseWrapperPosts getAllPosts(
            @RequestParam(required = false, value = "page_number", defaultValue = "0") @Min(0) Integer pageNumber,
            @RequestParam(required = false, value = "page_size", defaultValue = "10") Integer pageSize) {

        return postService.getAllPosts(pageNumber, pageSize);
    }

    @PostMapping("/search_posts")
    @Operation(
            summary = "Поиск постов по содержанию",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Получен список постов (Ok)",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = ResponseWrapperPosts.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Список постов не получен (Not Found)"
                    )
            }
    )
    public ResponseWrapperPosts searchPosts(
            @RequestParam(required = false, value = "page_number", defaultValue = "0") @Min(0) Integer pageNumber,
            @RequestParam(required = false, value = "page_size", defaultValue = "10") Integer pageSize,
            @RequestBody SearchPost dto) {

        return postService.searchPosts(pageNumber, pageSize, dto);
    }

    @GetMapping(value = "/image/{id}", produces = {
            MediaType.IMAGE_PNG_VALUE,
            MediaType.IMAGE_JPEG_VALUE,
            MediaType.APPLICATION_OCTET_STREAM_VALUE})
    @Operation(
            summary = "Получить картинку поста",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Картинка получена (OK)"),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Картинка не найдена (Not found)",
                            content = @Content())
            }
    )
    public byte[] getImage(@PathVariable("id") String id) {

        return pictureService.loadImagePost(id);
    }

//    @GetMapping("/found_posts/by_title")
//    @Operation(
//            summary = "Поиск постов по названию",
//            responses = {
//                    @ApiResponse(
//                            responseCode = "200",
//                            description = "Получен список постов (Ok)",
//                            content = @Content(
//                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
//                                    schema = @Schema(implementation = ResponseWrapperPosts.class)
//                            )
//                    ),
//                    @ApiResponse(
//                            responseCode = "404",
//                            description = "Список постов не получен (Not Found)"
//                    )
//            }
//    )
//    public ResponseWrapperPosts findByTitle(
//            @RequestParam(required = false, value = "page_number", defaultValue = "0") @Min(0) Integer pageNumber,
//            @RequestParam(required = false, value = "page_size", defaultValue = "10") Integer pageSize,
//            @RequestParam(required = false) String title) {
//
//        return postService.findByTitlePost(pageNumber, pageSize, title);
//    }
//
//    @GetMapping("/found_posts/by_content")
//    @Operation(
//            summary = "Поиск постов по содержанию",
//            responses = {
//                    @ApiResponse(
//                            responseCode = "200",
//                            description = "Получен список постов (Ok)",
//                            content = @Content(
//                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
//                                    schema = @Schema(implementation = ResponseWrapperPosts.class)
//                            )
//                    ),
//                    @ApiResponse(
//                            responseCode = "404",
//                            description = "Список постов не получен (Not Found)"
//                    )
//            }
//    )
//    public ResponseWrapperPosts findByContent(
//            @RequestParam(required = false, value = "page_number", defaultValue = "0") @Min(0) Integer pageNumber,
//            @RequestParam(required = false, value = "page_size", defaultValue = "10") Integer pageSize,
//            @RequestParam(required = false) String content) {
//
//        return postService.findByContentPost(pageNumber, pageSize, content);
//    }
}
