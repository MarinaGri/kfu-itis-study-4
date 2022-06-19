package ru.itis.cms.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.itis.cms.models.Article;
import ru.itis.cms.models.Role;

import java.util.List;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ArticleResponse {

    private String title;

    private String slug;

    private String text;

    private Boolean isForAdmins;

    public static ArticleResponse from(Article article) {
        return ArticleResponse.builder()
                .title(article.getTitle())
                .slug(article.getSlug())
                .text(article.getText())
                .isForAdmins(article.getUserRole().getAuthority()
                        .equals(Role.ROLE_ADMIN.name()))
                .build();
    }

    public static List<ArticleResponse> from(List<Article> articles) {
        return articles.stream().map(ArticleResponse::from).collect(Collectors.toList());
    }
}
