package ru.itis.blog.util.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.itis.blog.dto.response.PostResponse;
import ru.itis.blog.models.Post;

import java.util.List;

@Mapper(componentModel = "spring", uses = {FileInfoMapper.class, AuthorMapper.class})
public interface PostMapper {

    @Mapping(target = "authorId", expression = "java(post.getAuthor().getId())")
    @Mapping(target = "fileLink", source = "fileInfo")
    PostResponse toResponse(Post post);

    List<PostResponse> toResponses(List<Post> posts);

}
