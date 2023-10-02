package ru.otus.service;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.dao.AuthorDao;
import ru.otus.dao.BookDao;
import ru.otus.dao.GenreDao;
import ru.otus.domain.Author;
import ru.otus.domain.Book;
import ru.otus.domain.Genre;
import ru.otus.errors.LibraryErrorCode;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@Service
public class BookManagerImpl implements BookManager {

    private final BookDao bookDao;

    private final AuthorDao authorDao;

    private final GenreDao genreDao;

    @Transactional
    @Override
    public Book createBook(String title, String authorName, String genreName) {
        Author author = authorDao.getAuthorByName(authorName);
        Genre genre = genreDao.getGenreByName(genreName);

        if (author == null) {
            return null;
        }

        if (genre == null) {
            return null;
        }

        Book book = new Book(0, title, author, genre, new ArrayList<>());
        book = bookDao.insertBook(book);
        return book;
    }

    @Override
    public Book getBookById(long id) {
        Optional<Book> bookOpt = bookDao.getBookById(id);
        if (!bookOpt.isEmpty()) {
            return bookOpt.get();
        } else {
            return null;
        }
    }

    @Override
    public List<Book> getAllBooks() {
        return bookDao.getAllBooks();
    }

    @Transactional
    @Override
    public LibraryErrorCode updateBook(long id, String title, String authorName, String genreName) {
        if (!bookExists(id)) {
            return LibraryErrorCode.ERR_BOOK_NOT_FOUND;
        }

        Author author = authorDao.getAuthorByName(authorName);
        Genre genre = genreDao.getGenreByName(genreName);

        if (author == null) {
            return LibraryErrorCode.ERR_AUTHOR_NOT_FOUND;
        }

        if (genre == null) {
            return LibraryErrorCode.ERR_GENRE_NOT_FOUND;
        }

        Book book = new Book(1, title, author, genre, new ArrayList<>());
        bookDao.updateBook(book);

        return LibraryErrorCode.ERR_OK;
    }

    @Transactional
    @Override
    public LibraryErrorCode deleteBook(long id) {
        if (!bookExists(id)) {
            return LibraryErrorCode.ERR_BOOK_NOT_FOUND;
        }

        bookDao.deleteBook(new Book(id, "",  null, null, new ArrayList<>()));
        return LibraryErrorCode.ERR_OK;
    }

    private boolean bookExists(long id) {
        Optional<Book> bookOpt = bookDao.getBookById(id);
        if (!bookOpt.isEmpty()) {
            return true;
        } else {
            return false;
        }
    }
}
