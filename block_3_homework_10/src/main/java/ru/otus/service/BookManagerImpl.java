package ru.otus.service;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.dao.AuthorDao;
import ru.otus.dao.BookDao;
import ru.otus.dao.GenreDao;
import ru.otus.domain.Author;
import ru.otus.domain.Book;
import ru.otus.domain.Comment;
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
        Optional<Author> author = authorDao.findByName(authorName);
        Optional<Genre> genre = genreDao.findByName(genreName);

        if (author.isEmpty()) {
            return null;
        }

        if (genre.isEmpty()) {
            return null;
        }

        Book book = new Book(0, title, author.get(), genre.get(), new ArrayList<>());
        book = bookDao.save(book);
        return book;
    }

    @Override
    public Book getBookById(long id) {
        Optional<Book> bookOpt = bookDao.findById(id);
        if (!bookOpt.isEmpty()) {
            Book book = bookOpt.get();
            // Подгружаем комментарии
            for (Comment comment : book.getComments()) {
            }
            return book;
        } else {
            return null;
        }
    }

    @Override
    public List<Book> getAllBooks() {
        List<Book> books = bookDao.findAll();
        for (Book book : books) {
            // Подгружаем комментарии
            for (Comment comment : book.getComments()) {
            }
        }
        return books;
    }

    @Transactional
    @Override
    public LibraryErrorCode updateBook(long id, String title, String authorName, String genreName) {
        Optional<Book> bookOpt = bookDao.findById(id);
        if (bookOpt.isEmpty()) {
            return LibraryErrorCode.ERR_BOOK_NOT_FOUND;
        }

        Optional<Author> author = authorDao.findByName(authorName);
        Optional<Genre> genre = genreDao.findByName(genreName);

        if (author.isEmpty()) {
            return LibraryErrorCode.ERR_AUTHOR_NOT_FOUND;
        }

        if (genre.isEmpty()) {
            return LibraryErrorCode.ERR_GENRE_NOT_FOUND;
        }

        Book book = bookOpt.get();
        book.setTitle(title);
        book.setAuthor(author.get());
        book.setGenre(genre.get());
        bookDao.save(book);

        return LibraryErrorCode.ERR_OK;
    }

    @Transactional
    @Override
    public LibraryErrorCode deleteBook(long id) {
        if (!bookExists(id)) {
            return LibraryErrorCode.ERR_BOOK_NOT_FOUND;
        }

        bookDao.delete(new Book(id, "",  null, null, new ArrayList<>()));
        return LibraryErrorCode.ERR_OK;
    }

    private boolean bookExists(long id) {
        Optional<Book> bookOpt = bookDao.findById(id);
        if (!bookOpt.isEmpty()) {
            return true;
        } else {
            return false;
        }
    }
}
