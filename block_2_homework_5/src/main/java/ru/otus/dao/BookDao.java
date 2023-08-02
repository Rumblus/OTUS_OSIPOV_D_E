package ru.otus.dao;

import ru.otus.domain.Book;

import java.util.List;
import java.util.Optional;

public interface BookDao {
    Book insertBook(Book book);

    Optional<Book> getBookById(long id);

    List<Book> getAllBooks();

    void updateBook(long id, Book book);

    void deleteBook(long id);


}
