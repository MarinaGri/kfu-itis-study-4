package ru.itis.blog.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.itis.blog.models.Post;

import java.util.List;
import java.util.stream.Collectors;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Schema(description = "Post")
public class PostResponse {

    @Schema(description = "ID number", example = "1")
    private Long id;

    @Schema(description = "Title", example = "How to cook dumplings")
    private String title;

    @Schema(description = "Content of post", example = "Way 1..")
    private String text;

    @Schema(description = "Date of creation", example = "03-04-2022")
    private String createdAt;

    @Schema(description = "ID of author", example = "1")
    private Long authorId;

    @Schema(description = "Link to the attached file")
    private FileLink fileLink;

}
