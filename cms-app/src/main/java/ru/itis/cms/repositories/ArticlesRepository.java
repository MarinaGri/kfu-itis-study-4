package ru.itis.cms.repositories;


import org.springframework.data.repository.PagingAndSortingRepository;
import ru.itis.cms.models.Article;

import java.util.Optional;

public interface ArticlesRepository extends PagingAndSortingRepository<Article, Long> {

    Optional<Article> findBySlug(String slug);

}
