package ru.otus.dao;

import ru.otus.domain.Book;

import java.util.List;
import java.util.Optional;

public interface BookDao {
    Book insertBook(Book book);

    Optional<Book> getBookById(long id);

    Optional<Book> getBookByName(String name);

    List<Book> getAllBooks();

    void updateBook(Book book);

    void deleteBook(Book book);


}
