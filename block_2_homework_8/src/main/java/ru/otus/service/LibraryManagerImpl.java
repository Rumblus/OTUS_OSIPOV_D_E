package ru.otus.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.domain.Author;
import ru.otus.domain.Book;
import ru.otus.domain.Genre;
import ru.otus.errors.LibraryErrorCode;
import java.util.List;

@AllArgsConstructor
@Service
public class LibraryManagerImpl implements LibraryManager {

    private final BookManager bookManager;

    private final AuthorManager authorManager;

    private final GenreManager genreManager;

    private final CommentManager commentManager;


    @Override
    public Book createBook(String title, String authorName, String genreName) {
        return bookManager.createBook(title, authorName, genreName);
    }

    @Override
    public Book getBookById(String id) {
        return bookManager.getBookById(id);
    }

    @Override
    public List<Book> getAllBooks() {
        return bookManager.getAllBooks();
    }

    @Override
    public LibraryErrorCode updateBook(String id, String title, String authorName, String genreName) {
        return bookManager.updateBook(id, title, authorName, genreName);
    }

    @Override
    public LibraryErrorCode deleteBook(String id) {
        return bookManager.deleteBook(id);
    }

    @Override
    public List<Author> getAllAuthors() {
        return authorManager.getAllAuthors();
    }

    @Override
    public List<Genre> getAllGenres() {
        return genreManager.getAllGenres();
    }

    @Override
    public LibraryErrorCode createComment(String bookName, String commentData) {
        return commentManager.createComment(bookName, commentData);
    }

    @Override
    public LibraryErrorCode deleteComment(String bookName, int commentId) {
        return commentManager.deleteComment(bookName, commentId);
    }
}
