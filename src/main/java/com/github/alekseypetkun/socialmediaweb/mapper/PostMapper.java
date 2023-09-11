package com.github.alekseypetkun.socialmediaweb.mapper;

import com.github.alekseypetkun.socialmediaweb.dto.*;
import com.github.alekseypetkun.socialmediaweb.entity.Post;
import org.mapstruct.*;

/**
 * Маппинг сущности поста
 */
@Mapper(componentModel = "spring")
public interface PostMapper {

    /**
     * Преобразует сущность в дто
     *
     * @param entity сущность
     * @return дто
     */
    @Mapping(source = "author.id", target = "authorPost")
    PostDto mapToPostDto(Post entity);

    /**
     * Преобразует дто в сущность
     *
     * @param dto дто
     * @return сущность
     */
    @InheritInverseConfiguration
    Post mapToPostDto(CreatePost dto);

    /**
     * Преобразует дто в сущность не изменяя поля на null
     *
     * @param dto    ДТО
     * @param entity сущность
     */
    @Mapping(source = "title", target = "title",
            nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(source = "content", target = "content",
            nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void patch(UpdatePost dto, @MappingTarget Post entity);

    /**
     * Преобразует сущность в дто
     *
     * @param entity сущность
     * @return дто
     */
    @Mapping(source = "author.firstName", target = "authorFirstName")
    @Mapping(source = "author.lastName", target = "authorLastName")
    @Mapping(source = "id", target = "postId")
    FullPost mapToFullPost(Post entity);
}
