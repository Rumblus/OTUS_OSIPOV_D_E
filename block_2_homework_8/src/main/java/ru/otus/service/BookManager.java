package ru.otus.service;

import ru.otus.domain.Book;
import ru.otus.errors.LibraryErrorCode;

import java.util.List;

public interface BookManager {

    Book createBook(String title, String authorName, String genreName);

    Book getBookById(String id);

    List<Book> getAllBooks();

    LibraryErrorCode updateBook(String id, String title, String authorName, String genreName);

    LibraryErrorCode deleteBook(String id);
}
