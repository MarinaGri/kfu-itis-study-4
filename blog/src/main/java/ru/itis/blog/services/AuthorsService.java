package ru.itis.blog.services;

import ru.itis.blog.dto.AuthorDto;
import ru.itis.blog.dto.page.AuthorsPage;


public interface AuthorsService {
    AuthorsPage getAuthors(int page);

    AuthorDto addAuthor(AuthorDto author);

    AuthorDto updateAuthor(Long authorId, AuthorDto newData);
}

