package ru.itis.cms.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import ru.itis.cms.aspects.Loggable;
import ru.itis.cms.models.User;
import ru.itis.cms.services.ArticlesService;


@RequiredArgsConstructor
@Controller
public class ArticlesController {

    private final ArticlesService articlesService;

    @Loggable
    @PreAuthorize("isAuthenticated()")
    @GetMapping("/{slug}")
    public String getArticle(@AuthenticationPrincipal User user, @PathVariable("slug") String slug, ModelMap map) {
        map.put("user", user);
        map.put("article", articlesService.getArticleBySlug(slug, user.getAuthority()));
        return "article";
    }

    @Loggable
    @PreAuthorize("isAuthenticated()")
    @GetMapping("/articles")
    public String getArticles(@AuthenticationPrincipal User user,
                              @RequestParam(required = false) Integer page, ModelMap map) {
        map.put("user", user);
        map.put("page", articlesService.getArticles(page));
        return "articles_page";
    }

}
