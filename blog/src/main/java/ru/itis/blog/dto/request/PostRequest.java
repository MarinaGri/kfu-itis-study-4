package ru.itis.blog.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;
import ru.itis.blog.validation.annotations.Naming;

import javax.validation.constraints.NotNull;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Schema(description = "Post")
public class PostRequest {

    @Schema(description = "ID number", example = "1")
    private Long id;

    @NotNull
    @Naming(message = "Can contain letters, numbers, single spaces, _ and -")
    @Schema(description = "Title", example = "How to cook dumplings")
    private String title;

    @Schema(description = "Content of post", example = "Way 1..")
    private String text;

    private MultipartFile file;

}
