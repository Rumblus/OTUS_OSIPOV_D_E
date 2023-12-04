package ru.otus.dao;

import ru.otus.domain.Author;

import java.util.List;

public interface AuthorDao {
    Author getAuthorById(long id);

    Author getAuthorByName(String authorName);

    List<Author> getAllAuthors();
}
