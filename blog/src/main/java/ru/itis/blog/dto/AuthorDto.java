package ru.itis.blog.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.itis.blog.models.Author;
import ru.itis.blog.validation.annotations.FirstCapitalLetter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;
import java.util.stream.Collectors;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Schema(description = "Author")
public class AuthorDto {

    @NotNull
    @FirstCapitalLetter(message = "First letter must be capitalized")
    @Size(min = 3, max = 20, message = "Length {min}-{max} characters")
    @Schema(description = "ID number", example = "1")
    private Long id;

    @NotNull
    @FirstCapitalLetter(message = "First letter must be capitalized")
    @Size(min = 3, max = 20, message = "Length {min}-{max} characters")
    @Schema(description = "First name", example = "Marat")
    private String firstName;

    @NotBlank
    @Schema(description = "Last name", example = "Arslanov")
    private String lastName;

}

