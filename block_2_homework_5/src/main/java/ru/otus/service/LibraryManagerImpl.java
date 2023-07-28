package ru.otus.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.dao.LibraryDao;
import ru.otus.domain.Author;
import ru.otus.domain.Book;
import ru.otus.domain.Genre;

import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@Service
public class LibraryManagerImpl implements LibraryManager {
    private LibraryDao libraryDao;

    @Override
    public void createBook(String title, long authorId, long genreId) {
        if (!authorExists(authorId)) {
            System.out.println("No author with id = " + authorId + " found!");
            return;
        }
        if (!genreExists(genreId)) {
            System.out.println("No genre with id = " + genreId + " found!");
            return;
        }

        Book book = new Book(1, title, authorId, genreId);
        book = libraryDao.insertBook(book);
        System.out.println("Book created! ID is: " + book.getId());
    }

    @Override
    public void getBookById(long id) {
        Optional<Book> bookOpt = libraryDao.getBookById(id);
        if (!bookOpt.isEmpty()) {
            Book book = bookOpt.get();
            System.out.println("Book\n\tid: " + book.getId() +
                                "\n\ttitle: " + book.getTitle() +
                                "\n\tauthorId: " + libraryDao.getAuthorById(book.getAuthorId()) +
                                "\n\tgenreId: " + libraryDao.getGenreById(book.getGenreId()));
        } else {
            System.out.println("No book with id = " + id + " found!");
        }
    }

    @Override
    public void getAllBooks() {
        List<Book> bookList = libraryDao.getAllBooks();

        if (bookList.isEmpty()) {
            System.out.println("The Library is empty...");
            return;
        }

        for (Book book : bookList) {
            System.out.println("Book\n\tid: " + book.getId() +
                    "\n\ttitle: " + book.getTitle() +
                    "\n\tauthorId: " + libraryDao.getAuthorById(book.getAuthorId()) +
                    "\n\tgenreId: " + libraryDao.getGenreById(book.getGenreId()) + "\n");
        }
    }

    @Override
    public void updateBook(long id, String title, long authorId, long genreId) {
        if (!bookExists(id)) {
            System.out.println("No book with id = " + id + " found!");
            return;
        }
        if (!authorExists(authorId)) {
            System.out.println("No author with id = " + authorId + " found!");
            return;
        }
        if (!genreExists(genreId)) {
            System.out.println("No genre with id = " + genreId + " found!");
            return;
        }

        Book book = new Book(1, title, authorId, genreId);
        libraryDao.updateBook(id, book);
        System.out.println("Book was updated!");
    }

    @Override
    public void deleteBook(long id) {
        if (!bookExists(id)) {
            System.out.println("No book with id = " + id + " found!");
            return;
        }

        libraryDao.deleteBook(id);
        System.out.println("Book was deleted!");
    }

    @Override
    public void getAllAuthors() {
        List<Author> authorList = libraryDao.getAllAuthors();

        if (authorList.isEmpty()) {
            System.out.println("The are no authors in the library...");
            return;
        }

        for (Author author : authorList) {
            System.out.println("Author\n\tid: " + author.getId() +
                    "\n\tname: " + author.getName() + "\n");
        }
    }

    @Override
    public void getAllGenres() {
        List<Genre> genreList = libraryDao.getAllGenres();

        if (genreList.isEmpty()) {
            System.out.println("The are no genres in the library...");
            return;
        }

        for (Genre genre : genreList) {
            System.out.println("Genre\n\tid: " + genre.getId() +
                    "\n\tname: " + genre.getName() + "\n");
        }
    }

    private boolean bookExists(long id) {
        Optional<Book> bookOpt = libraryDao.getBookById(id);
        if (!bookOpt.isEmpty()) {
            return true;
        } else {
            return false;
        }
    }

    private boolean authorExists(long id) {
        Optional<String> author = Optional.ofNullable(libraryDao.getAuthorById(id));
        if (author.isEmpty()) {
            return false;
        } else {
            return true;
        }
    }

    private boolean genreExists(long id) {
        Optional<String> genre = Optional.ofNullable(libraryDao.getGenreById(id));
        if (genre.isEmpty()) {
            return false;
        } else {
            return true;
        }
    }
}
