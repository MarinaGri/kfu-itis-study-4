package ru.itis.cms.dto.page;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.itis.cms.dto.response.ArticleResponse;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ArticlesPage {

    private Integer index;

    private List<ArticleResponse> articles;

    private Integer totalPages;
}
