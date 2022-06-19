package ru.itis.blog.services;

import org.springframework.web.multipart.MultipartFile;
import ru.itis.blog.dto.request.PostRequest;
import ru.itis.blog.dto.page.PostsPage;
import ru.itis.blog.dto.response.PostResponse;


public interface PostsService {

    PostResponse addPost(Long authorId, PostRequest post);

    PostResponse updatePost(Long authorId, PostRequest newData);

    void deletePost(Long authorId, Long postId);

    PostsPage getPosts(Long authorId, int page, String sort, String order);
}
