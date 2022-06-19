package ru.itis.cms.services;

import ru.itis.cms.dto.response.ArticleResponse;
import ru.itis.cms.dto.form.ArticleForm;
import ru.itis.cms.dto.page.ArticlesPage;
import ru.itis.cms.exceptions.InvalidSlugException;
import ru.itis.cms.models.Article;
import ru.itis.cms.models.UserAuthority;

public interface ArticlesService {

    Article save(ArticleForm form) throws InvalidSlugException;

    ArticlesPage getArticles(Integer page);

    ArticleResponse getArticleBySlug(String slug, UserAuthority authority);
}
