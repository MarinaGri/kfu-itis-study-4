package ru.itis.blog.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.itis.blog.dto.AuthorDto;
import ru.itis.blog.dto.page.AuthorsPage;
import ru.itis.blog.services.AuthorsService;

import javax.validation.Valid;


@RequiredArgsConstructor
@RestController
@RequestMapping("/authors")
public class AuthorsController {

    private final AuthorsService authorsService;

    @Operation(summary = "Getting authors with Pagination")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Page with authors",
                    content = {
                            @Content(mediaType = "application/json",
                                    schema =
                                    @Schema(implementation = AuthorsPage.class)
                            )
                    }
            )
    })
    @GetMapping
    public ResponseEntity<AuthorsPage> getAuthors(
            @Parameter(description = "Page number") @RequestParam("page") int page) {
        return ResponseEntity.ok(authorsService.getAuthors(page));
    }

    @Operation(summary = "Adding an author")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201",
                    content = {
                            @Content(mediaType = "application/json",
                                    schema =
                                    @Schema(implementation = AuthorDto.class,
                                            description = "New author"
                                    )
                            )
                    }
            )
    })
    @PutMapping
    public ResponseEntity<AuthorDto> addAuthor(
            @Parameter(description = "Data of the new author") @Valid @RequestBody AuthorDto author) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(authorsService.addAuthor(author));
    }

    @Operation(summary = "Updating information about the author")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "202",
                    content = {
                            @Content(mediaType = "application/json",
                                    schema =
                                    @Schema(implementation = AuthorDto.class,
                                            description = "Changed author"
                                    )
                            )
                    }
            )
    })
    @PatchMapping("/{author-id}")
    public ResponseEntity<AuthorDto> updateAuthor(@PathVariable("author-id") Long authorId,
           @Parameter(description = "Changed author") @Valid @RequestBody AuthorDto newData) {
        return ResponseEntity
                .status(HttpStatus.ACCEPTED)
                .body(authorsService.updateAuthor(authorId, newData));
    }

}
