package ru.otus.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import ru.otus.dao.AuthorDaoJdbc;
import ru.otus.dao.BookDaoJdbc;
import ru.otus.dao.GenreDaoJdbc;
import ru.otus.domain.Author;
import ru.otus.domain.Book;
import ru.otus.domain.Genre;

import static org.assertj.core.api.Assertions.*;

@DisplayName("Тесты сервисного класса библиотеки")
@JdbcTest(properties = "spring.shell.interactive.enabled=false")
@ExtendWith(SpringExtension.class)
@Import({LibraryManagerImpl.class, BookDaoJdbc.class, AuthorDaoJdbc.class, GenreDaoJdbc.class})
public class LibraryManagerImplTest {
    @Autowired
    private BookDaoJdbc bookdao;

    @Autowired
    private AuthorDaoJdbc authorDao;

    @Autowired
    private GenreDaoJdbc genreDao;

    @Autowired
    private LibraryManagerImpl libraryManager;

    @DisplayName("Должен добавлять книгу в библиотеку")
    @Test
    public void shouldCreateBook() {
        Book book = new Book(3, "Test Book", new Author(2, "Lev Tolstoi"), new Genre(2, "Fairy Tale"));
        libraryManager.createBook(book.getTitle(), book.getAuthor().getName(), book.getGenre().getName());

        Book createdBook = libraryManager.getBookById(book.getId());

        assertThat(createdBook).usingRecursiveComparison().isEqualTo(book);
    }

    @DisplayName("Должен возвращать книгу по ID")
    @Test
    public void shouldGetBookById() {
        Book book = new Book(1, "Black Arrow", new Author(1, "Robert Lewis Stevenson"), new Genre(1, "Novel"));
        Book actualBook = libraryManager.getBookById(book.getId());

        assertThat(actualBook).usingRecursiveComparison().isEqualTo(book);
    }

    @DisplayName("Должен редактировать книгу по ID")
    @Test
    public void shouldUpdateBook() {
        Book book = new Book(1, "TestBook", new Author(2, "Lev Tolstoi"), new Genre(2, "Fairy Tale"));
        libraryManager.updateBook(book.getId(), book.getTitle(), book.getAuthor().getName(), book.getGenre().getName());

        Book actualBook = libraryManager.getBookById(book.getId());

        assertThat(actualBook).usingRecursiveComparison().isEqualTo(book);
    }

    @DisplayName("Должен удалять книгу")
    @Test
    public void shouldDeleteBook() {
        long idToDelete = 2;
        libraryManager.deleteBook(idToDelete);

        Book actualBook = libraryManager.getBookById(idToDelete);

        assertThat(actualBook).isNull();
    }
}
