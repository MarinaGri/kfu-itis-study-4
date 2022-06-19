package ru.itis.blog.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.itis.blog.models.Author;

public interface AuthorsRepository extends JpaRepository<Author, Long> {
}
