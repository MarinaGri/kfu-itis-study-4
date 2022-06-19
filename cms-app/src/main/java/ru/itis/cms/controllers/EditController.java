package ru.itis.cms.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import ru.itis.cms.dto.form.ArticleForm;
import ru.itis.cms.exceptions.InvalidSlugException;
import ru.itis.cms.models.Article;
import ru.itis.cms.models.User;
import ru.itis.cms.services.ArticlesService;

import javax.validation.Valid;

@RequiredArgsConstructor
@RequestMapping("/articles/edit")
@Controller
public class EditController {

    private final ArticlesService articlesService;

    @GetMapping
    @PreAuthorize("hasRole(T(ru.itis.cms.models.Role).ROLE_ADMIN.name())")
    public String getEditPage(@AuthenticationPrincipal User user, ModelMap map) {
        map.put("user", user);
        map.put("form", new ArticleForm());
        return "edit_page";
    }

    @PostMapping
    @PreAuthorize("hasRole(T(ru.itis.cms.models.Role).ROLE_ADMIN.name())")
    public String addArticle(@AuthenticationPrincipal User user, RedirectAttributes redirectAttributes,
                             @ModelAttribute("form") @Valid ArticleForm form, BindingResult result, ModelMap map) {
        map.put("user", user);
        if (result.hasErrors()) {
            map.put("form", form);
            return "edit_page";
        }
        Article article;
        try {
            article = articlesService.save(form);
        } catch (InvalidSlugException ex) {
            map.put("form", form);
            result.rejectValue("title", "error.slug");
            return "edit_page";
        }
        redirectAttributes.addFlashAttribute("slug", article.getSlug());
        return "redirect:/articles/edit";
    }

}
