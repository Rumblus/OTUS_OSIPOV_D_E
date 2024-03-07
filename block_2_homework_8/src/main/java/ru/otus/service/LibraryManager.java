package ru.otus.service;

import ru.otus.domain.Author;
import ru.otus.domain.Book;
import ru.otus.domain.Genre;
import ru.otus.errors.LibraryErrorCode;

import java.util.List;

public interface LibraryManager {
    Book createBook(String title, String authorName, String genreName);

    Book getBookById(String id);

    List<Book> getAllBooks();

    LibraryErrorCode updateBook(String id, String title, String authorName, String genreName);

    LibraryErrorCode deleteBook(String id);

    List<Author> getAllAuthors();

    List<Genre> getAllGenres();

    LibraryErrorCode createComment(String bookName, String commentData);

    LibraryErrorCode deleteComment(String bookName, int commentId);
}
