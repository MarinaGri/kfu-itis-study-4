package ru.itis.blog.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.itis.blog.dto.request.PostRequest;
import ru.itis.blog.dto.page.PostsPage;
import ru.itis.blog.dto.response.PostResponse;
import ru.itis.blog.services.PostsService;

import javax.validation.Valid;


@RequiredArgsConstructor
@RestController
@RequestMapping("/authors")
public class PostController {

    private final PostsService postsService;

    @Operation(summary = "Adding a post to a specific author")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201",
                    content = {
                            @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema =
                                    @Schema(implementation = PostRequest.class,
                                            description = "New post"
                                    )
                            )
                    }
            )
    })
    @PutMapping(value = "/{author-id}/posts", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<PostResponse> addPost(
            @PathVariable("author-id") Long authorId,
            @Valid PostRequest post) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(postsService.addPost(authorId, post));
    }

    @Operation(summary = "Getting posts of a certain author with pagination and sorting")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Page with posts by a certain author",
                    content = {
                            @Content(mediaType = "application/json",
                                    schema =
                                    @Schema(implementation = PostsPage.class)
                            )
                    }
            )
    })
    @GetMapping("/{author-id}/posts")
    public ResponseEntity<PostsPage> getPosts(
            @PathVariable("author-id") Long authorId,
            @Parameter(description = "Page number", example = "0") @RequestParam("page") int page,
            @Parameter(description = "Post field for sorting", example = "text") @RequestParam("sort") String sort,
            @Parameter(description = "Sort order", example = "desc") @RequestParam("order") String order) {
        return ResponseEntity.ok(postsService.getPosts(authorId, page, sort, order));
    }


    @Operation(summary = "Changing a certain post by a certain author")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "202",
                    content = {
                            @Content(mediaType = "application/json",
                                    schema =
                                    @Schema(implementation = PostRequest.class,
                                            description = "Modified post"
                                    )
                            )
                    }
            )
    })
    @PatchMapping(value = "/{author-id}/posts/{post-id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<PostResponse> updatePost(
            @PathVariable("author-id") Long authorId, @PathVariable("post-id") Long postId,
            @Valid PostRequest newData) {
        newData.setId(postId);
        return ResponseEntity
                .status(HttpStatus.ACCEPTED)
                .body(postsService.updatePost(authorId, newData));
    }

    @Operation(summary = "Deleting a certain post by a certain author")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "202"
            )
    })
    @DeleteMapping("/{author-id}/posts/{post-id}")
    public ResponseEntity<?> deletePost(
            @PathVariable("author-id") Long authorId,
            @PathVariable("post-id") Long postId) {
        postsService.deletePost(authorId, postId);
        return ResponseEntity
                .status(HttpStatus.ACCEPTED)
                .build();
    }

}
