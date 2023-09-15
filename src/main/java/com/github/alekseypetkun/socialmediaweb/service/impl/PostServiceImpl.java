package com.github.alekseypetkun.socialmediaweb.service.impl;

import com.github.alekseypetkun.socialmediaweb.dto.*;
import com.github.alekseypetkun.socialmediaweb.entity.Post;
import com.github.alekseypetkun.socialmediaweb.entity.Subscriber;
import com.github.alekseypetkun.socialmediaweb.entity.User;
import com.github.alekseypetkun.socialmediaweb.exception.AuthorisationException;
import com.github.alekseypetkun.socialmediaweb.exception.NotFoundPostException;
import com.github.alekseypetkun.socialmediaweb.mapper.PostMapper;
import com.github.alekseypetkun.socialmediaweb.repository.PostRepository;
import com.github.alekseypetkun.socialmediaweb.repository.SubscriberRepository;
import com.github.alekseypetkun.socialmediaweb.service.AuthService;
import com.github.alekseypetkun.socialmediaweb.service.PictureService;
import com.github.alekseypetkun.socialmediaweb.service.PostService;
import com.github.alekseypetkun.socialmediaweb.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Бизнес-логика по работе с постами
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {

    private final UserService userService;
    private final AuthService authService;
    private final PostRepository postRepository;
    private final PostMapper postMapper;
    private final PictureService pictureService;
    private final SubscriberRepository subscriberRepository;

    @Override
    public PostDto addPost(CreatePost dto, MultipartFile image, Long userId) {

        TimeZone timeZone = TimeZone.getDefault();
        LocalDateTime dateTimeNow = LocalDateTime.now(timeZone.toZoneId());

        String imageId = pictureService.addImage(image);

        User author = userService.findUserById(userId);
        Post post = postMapper.mapToPostDto(dto);
        post.setDateTimePost(dateTimeNow);
        post.setImage(imageId);
        post.setAuthor(author);

        PostDto newPost = postMapper.mapToPostDto(postRepository.save(post));
        log.info("IN addPost - post: {} created", post);
        return newPost;
    }

    @Override
    public PostDto updatePostById(Long postId, UpdatePost dto, Long userId) {

        User authorOrAdmin = userService.findUserById(userId);
        Post post = findPostById(postId);

        if (authService.checkAccess(post, authorOrAdmin)) {

            postMapper.patch(dto, post);

            PostDto updatedPost = postMapper.mapToPostDto(postRepository.save(post));
            log.info("IN updatePost - post: {} updated", post);
            return updatedPost;

        } else {
            throw new AuthorisationException("user is forbidden", "8081_USER_IS_FORBIDDEN");
        }
    }

    @Override
    public void deletePostById(Long postId, Long userId) {

        User authorOrAdmin = userService.findUserById(userId);
        Post post = findPostById(postId);

        if (authService.checkAccess(post, authorOrAdmin)) {

            postRepository.deleteById(postId);
            log.info("IN deletePost - post: {} deleted", post);
        } else {
            throw new AuthorisationException("user is forbidden", "8082_USER_IS_FORBIDDEN");
        }
    }

    @Override
    public ResponseWrapperPosts getAllPostsBySubscriptions(int pageNumber, int pageSize, Long userId) {

        User fromUserId = userService.findUserById(userId);

        List<Subscriber> friendsArrayList = subscriberRepository.findAllByFromUserId(fromUserId);

        List<User> subscriptionsUserArrayList = friendsArrayList.stream()
                .map(Subscriber::getToUserId)
                .toList();

        List<PostDto> dtoList = new ArrayList<>();
        for (User author : subscriptionsUserArrayList) {
            dtoList.addAll(postRepository
                    .findAllByAuthor(author).stream()
                    .map(postMapper::mapToPostDto)
                    .toList());
        }

        List<PostDto> dtoResult = dtoList.stream()
                .sorted(Comparator.comparing(PostDto::getDateTimePost).reversed())
                .skip(pageNumber)
                .limit(pageSize)
                .toList();

        return new ResponseWrapperPosts(dtoList.size(), dtoResult);
    }

    @Override
    public FullPost getPostById(Long postId) {

        return postMapper.mapToFullPost(findPostById(postId));
    }

    @Override
    public ResponseWrapperPosts getPostsMe(int pageNumber, int pageSize, Long userId) {

        User author = userService.findUserById(userId);

        List<PostDto> dtoList = new ArrayList<>(postRepository
                .findAllByAuthor(author).stream()
                .map(postMapper::mapToPostDto)
                .toList());

        List<PostDto> dtoResult = dtoList.stream()
                .sorted(Comparator.comparing(PostDto::getDateTimePost).reversed())
                .skip(pageNumber)
                .limit(pageSize)
                .toList();

        return new ResponseWrapperPosts(dtoList.size(), dtoResult);
    }

    @Override
    public boolean updatePostImage(Long postId, MultipartFile image, Long userId) {

        String imageId = pictureService.addImage(image);
        User authorOrAdmin = userService.findUserById(userId);
        Post postUpdated = findPostById(postId);

        if (authService.checkAccess(postUpdated, authorOrAdmin)) {

            postUpdated.setImage(imageId);
            postRepository.save(postUpdated);

            log.info("IN updatePostImage - post: {} updated", postUpdated);

        } else {
            throw new AuthorisationException("user is forbidden", "8082_USER_IS_FORBIDDEN");
        }

        return false;
    }

    @Override
    public Post findPostById(Long postId) {

        return postRepository.findById(postId)
                .orElseThrow(() -> new NotFoundPostException(postId));
    }

    @Override
    public ResponseWrapperPosts getAllPosts(int pageNumber, int pageSize) {

        List<PostDto> dtoList = new ArrayList<>(postRepository
                .findAll().stream()
                .map(postMapper::mapToPostDto)
                .toList());

        List<PostDto> dtoResult = dtoList.stream()
                .sorted(Comparator.comparing(PostDto::getDateTimePost).reversed())
                .skip(pageNumber)
                .limit(pageSize)
                .toList();

        return new ResponseWrapperPosts(dtoList.size(), dtoResult);
    }

    @Override
    public ResponseWrapperPosts searchPosts(int pageNumber, int pageSize, SearchPost dto) {

//        Pageable pageable = PageRequest
//                .of(pageNumber, pageSize, Sort.by(Sort.Order.desc("dateTimePost")));

        Set<PostDto> dtoList = postRepository
                .findAllByTitleContainingIgnoreCase(dto.getTitle())
                .stream()
                .map(postMapper::mapToPostDto)
                .filter(value -> dto.getContent() == null
                        || value.getContent().toLowerCase().contains(dto.getContent().toLowerCase()))
                .sorted(Comparator.comparing(PostDto::getDateTimePost).reversed())
                .skip(pageNumber)
                .limit(pageSize)
                .collect(Collectors.toSet());

        List<PostDto> dtoResult = dtoList.stream()
                .sorted(Comparator.comparing(PostDto::getDateTimePost).reversed())
                .skip(pageNumber)
                .limit(pageSize)
                .toList();

        return new ResponseWrapperPosts(dtoList.size(), dtoResult);
    }

//    @Override
//    public ResponseWrapperPosts findByTitlePost(int pageNumber, int pageSize, String title) {
//
//        List<PostDto> dtoList = new ArrayList<>(postRepository
//                .findAllByTitleContainingIgnoreCase(title).stream()
//                .map(postMapper::mapToPostDto)
//                .toList());
//
//        List<PostDto> dtoResult = dtoList.stream()
//                .sorted(Comparator.comparing(PostDto::getDateTimePost).reversed())
//                .skip(pageNumber)
//                .limit(pageSize)
//                .toList();
//
//        return new ResponseWrapperPosts(dtoList.size(), dtoResult);
//    }
//
//    @Override
//    public ResponseWrapperPosts findByContentPost(int pageNumber, int pageSize, String content) {
//
//        List<PostDto> dtoList = new ArrayList<>(postRepository
//                .findAllByContentContainingIgnoreCase(content).stream()
//                .map(postMapper::mapToPostDto)
//                .toList());
//
//        List<PostDto> dtoResult = dtoList.stream()
//                .sorted(Comparator.comparing(PostDto::getDateTimePost).reversed())
//                .skip(pageNumber)
//                .limit(pageSize)
//                .toList();
//
//        return new ResponseWrapperPosts(dtoList.size(), dtoResult);
//    }
}
