package ru.itis.blog.services.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import ru.itis.blog.dto.request.PostRequest;
import ru.itis.blog.dto.page.PostsPage;
import ru.itis.blog.dto.response.PostResponse;
import ru.itis.blog.exceptions.PostNotFoundException;
import ru.itis.blog.models.Author;
import ru.itis.blog.models.FileInfo;
import ru.itis.blog.models.Post;
import ru.itis.blog.repositories.AuthorsRepository;
import ru.itis.blog.repositories.PostsRepository;
import ru.itis.blog.services.FileService;
import ru.itis.blog.services.PostsService;
import ru.itis.blog.util.mappers.PostMapper;

import java.time.LocalDateTime;


@RequiredArgsConstructor
@Service
public class PostsServiceImpl implements PostsService {

    private final AuthorsRepository authorsRepository;

    private final PostsRepository postsRepository;

    private final FileService fileService;

    private final PostMapper postMapper;

    @Value("${blog.default-page-size}")
    private int defaultPageSize;

    @Override
    public PostResponse addPost(Long authorId, PostRequest post) {
        Author author = authorsRepository.getById(authorId);
        FileInfo fileInfo = fileService.upload(post.getFile());

        Post newPost = Post.builder()
                .createdAt(LocalDateTime.now())
                .title(post.getTitle())
                .text(post.getText())
                .author(author)
                .state(Post.State.PUBLISHED)
                .fileInfo(fileInfo)
                .build();

        return postMapper.toResponse(postsRepository.save(newPost));
    }

    @Override
    public PostResponse updatePost(Long authorId, PostRequest newData) {

        Post post = postsRepository.findByIdAndAuthor_Id(newData.getId(), authorId)
                .orElseThrow(PostNotFoundException::new);

        FileInfo fileInfo = fileService.upload(newData.getFile());

        post.setTitle(newData.getTitle());
        post.setText(newData.getText());
        post.setFileInfo(fileInfo);


        return postMapper.toResponse(postsRepository.save(post));
    }

    @Override
    public void deletePost(Long authorId, Long postId) {

        Post post = postsRepository.findByIdAndAuthor_Id(postId, authorId)
                .orElseThrow(PostNotFoundException::new);
        post.setState(Post.State.DELETED);

    }

    @Override
    public PostsPage getPosts(Long authorId, int page, String sort, String order) {

        PageRequest pageRequest = PageRequest.of(page, defaultPageSize,
                Sort.Direction.valueOf(order.toUpperCase()), sort.toLowerCase());

        Page<Post> postPage = postsRepository.findAllByAuthor_Id(authorId, pageRequest);
        return PostsPage.builder()
                .posts(postMapper.toResponses(postPage.getContent()))
                .totalPages(postPage.getTotalPages())
                .build();
    }

}
