package ru.itis.blog.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import ru.itis.blog.models.Post;

import java.util.Optional;

public interface PostsRepository extends JpaRepository<Post, Long> {

    Optional<Post> findByIdAndAuthor_Id(Long postId, Long authorId);

    Page<Post> findAllByAuthor_Id(Long authorId, Pageable pageable);
}
