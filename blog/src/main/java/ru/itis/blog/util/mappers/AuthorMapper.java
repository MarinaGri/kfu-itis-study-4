package ru.itis.blog.util.mappers;

import org.mapstruct.Mapper;
import ru.itis.blog.dto.AuthorDto;
import ru.itis.blog.models.Author;

import java.util.List;

@Mapper(componentModel = "spring")
public interface AuthorMapper {

    AuthorDto toResponse(Author author);

    List<AuthorDto> toResponses(List<Author> authors);
}
