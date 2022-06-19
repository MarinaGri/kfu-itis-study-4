package ru.itis.blog.dto.page;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.itis.blog.dto.request.PostRequest;
import ru.itis.blog.dto.response.PostResponse;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Schema(description = "Page with a list of posts and the total number of such pages")
public class PostsPage {

    private List<PostResponse> posts;

    @Schema(description = "Number of available pages")
    private Integer totalPages;
}
