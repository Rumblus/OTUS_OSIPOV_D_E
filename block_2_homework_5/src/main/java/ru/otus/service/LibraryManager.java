package ru.otus.service;

public interface LibraryManager {
    void createBook(String title, long authorId, long genreId);

    void getBookById(long id);

    void getAllBooks();

    void updateBook(long id, String title, long authorId, long genreId);

    void deleteBook(long id);

    void getAllAuthors();

    void getAllGenres();
}
