package ru.otus.service;

public interface LibraryManager {
    void createBook(String title, String authorName, String genreName);

    void getBookById(long id);

    void getAllBooks();

    void updateBook(long id, String title, String authorName, String genreName);

    void deleteBook(long id);

    void getAllAuthors();

    void getAllGenres();
}
