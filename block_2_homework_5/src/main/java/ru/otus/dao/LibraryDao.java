package ru.otus.dao;

import ru.otus.domain.Author;
import ru.otus.domain.Book;
import ru.otus.domain.Genre;

import java.util.List;
import java.util.Optional;

public interface LibraryDao {
    Book insertBook(Book book);

    Optional<Book> getBookById(long id);

    List<Book> getAllBooks();

    void updateBook(long id, Book book);

    void deleteBook(long id);

    Author getAuthorById(long id);

    Genre getGenreById(long id);

    List<Author> getAllAuthors();

    List<Genre> getAllGenres();

    Author getAuthorByName(String authorName);

    Genre getGenreByName(String genreName);
}