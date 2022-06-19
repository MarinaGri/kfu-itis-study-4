package ru.itis.cms.services.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;
import ru.itis.cms.aspects.Loggable;
import ru.itis.cms.dto.response.ArticleResponse;
import ru.itis.cms.dto.form.ArticleForm;
import ru.itis.cms.dto.page.ArticlesPage;
import ru.itis.cms.exceptions.*;
import ru.itis.cms.models.Article;
import ru.itis.cms.models.Role;
import ru.itis.cms.models.UserAuthority;
import ru.itis.cms.repositories.ArticlesRepository;
import ru.itis.cms.repositories.UserAuthoritiesRepository;
import ru.itis.cms.services.ArticlesService;
import ru.itis.cms.utils.*;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import static ru.itis.cms.dto.response.ArticleResponse.from;

@RequiredArgsConstructor
@Service
public class ArticlesServiceImpl implements ArticlesService {

    private final ArticlesRepository articlesRepository;

    private final UserAuthoritiesRepository userAuthRepository;

    private final SlugGenerator slugGenerator;

    private final SlugChecker slugChecker;

    private final HtmlCleaner htmlCleaner;


    @Value("${cms.default-page-size}")
    private int defaultPageSize;

    @Transactional
    @Override
    public Article save(ArticleForm form) throws InvalidSlugException {

        String slug = slugGenerator.generate(form.getTitle());

        Role role = form.getIsForAdmins() ? Role.ROLE_ADMIN : Role.ROLE_USER;

        UserAuthority userRole = userAuthRepository.findByAuthority(role)
                .orElseThrow(UserAuthNotFoundException::new);

        Article article = Article.builder()
                .text(form.getText())
                .title(form.getTitle())
                .userRole(userRole)
                .build();

        if (articlesRepository.findBySlug(slug).isPresent()) {
            article.setSlug(UUID.randomUUID().toString());
            article = articlesRepository.save(article);
            article.setSlug(slug + "-" + article.getId());
        } else {
            article.setSlug(slug);
        }

        checkSlug(article);

        return articlesRepository.save(article);
    }

    @Override
    public ArticlesPage getArticles(Integer page) {
        page = page == null ? 0 : page;
        PageRequest pageRequest = PageRequest.of(page, defaultPageSize);
        Page<Article> articlePage;

        articlePage = articlesRepository.findAll(pageRequest);
        List<ArticleResponse> articles = from(
                articlePage.getContent()
                        .stream()
                        .peek(article -> article.setText(
                                htmlCleaner.keepOnlySafeTags(article.getText()))
                        )
                        .collect(Collectors.toList())
        );

        return ArticlesPage.builder()
                .index(page)
                .articles(articles)
                .totalPages(articlePage.getTotalPages())
                .build();
    }

    @Override
    public ArticleResponse getArticleBySlug(String slug, UserAuthority role) {
        Article article = articlesRepository.findBySlug(slug).orElseThrow(ArticleNotFoundException::new);
        article.setText(htmlCleaner.keepOnlySafeTags(article.getText()));

        if (role.getAuthority().equals(Role.ROLE_ADMIN.name()) || article.getUserRole().equals(role)) {
            return from(article);
        }
        throw new NotHavePermissionsException("Access to this article is restricted");
    }

    @Loggable
    private void checkSlug(Article article) throws InvalidSlugException {
        String slug = article.getSlug();

        if (slugChecker.isEqualToSecurityPaths(slug) || slugChecker.isEqualToControllersPaths(slug)) {
            articlesRepository.delete(article);
            throw new InvalidSlugException("Slug " + slug + " matches the reserved value");
        }

    }
}
