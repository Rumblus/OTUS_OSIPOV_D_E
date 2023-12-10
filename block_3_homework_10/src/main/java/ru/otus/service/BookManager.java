package ru.otus.service;

import ru.otus.domain.Book;
import ru.otus.errors.LibraryErrorCode;

import java.util.List;

public interface BookManager {

    Book createBook(String title, String authorName, String genreName);

    Book getBookById(long id);

    List<Book> getAllBooks();

    LibraryErrorCode updateBook(long id, String title, String authorName, String genreName);

    LibraryErrorCode deleteBook(long id);
}
