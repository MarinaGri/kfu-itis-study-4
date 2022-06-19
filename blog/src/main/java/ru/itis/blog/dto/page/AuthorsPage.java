package ru.itis.blog.dto.page;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.itis.blog.dto.AuthorDto;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Schema(description = "Page with a list of authors and the total number of such pages")
public class AuthorsPage {

    private List<AuthorDto> authors;

    @Schema(description = "Number of available pages")
    private Integer totalPages;
}

