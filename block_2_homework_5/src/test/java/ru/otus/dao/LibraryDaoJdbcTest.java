package ru.otus.dao;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import ru.otus.domain.Author;
import ru.otus.domain.Book;
import ru.otus.domain.Genre;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;

@DisplayName("Тесты DAO класса библиотеки")
@JdbcTest(properties = "spring.shell.interactive.enabled=false")
@ExtendWith(SpringExtension.class)
@Import(LibraryDaoJdbc.class)
public class LibraryDaoJdbcTest {
    @Autowired
    private LibraryDaoJdbc libraryDao;

    @DisplayName("InsertBook")
    @Test
    public void shouldInsertBook() {
        Book expectedBook = new Book(2, "TestBook", 1, 2);
        Book insertedBook = libraryDao.insertBook(expectedBook);
        Book selectedBook = libraryDao.getBookById(insertedBook.getId()).get();
        assertThat(selectedBook).usingRecursiveComparison().isEqualTo(expectedBook);
    }

    @DisplayName("GetById")
    @Test
    public void shouldGetBookById() {
        Book bookThatExists = new Book(1, "Black Arrow", 1, 1);
        Book selectedBook = libraryDao.getBookById(bookThatExists.getId()).get();
        assertThat(selectedBook).usingRecursiveComparison().isEqualTo(bookThatExists);
    }

    @Test
    @DisplayName("GetAllBooks")
    public void shouldGetAllBooks() {
        List<Book> expectedList = List.of(
                new Book(1, "Black Arrow", 1, 1),
                new Book(2, "Sherlock Holmes", 2, 3)
        );

        List<Book> actualList = libraryDao.getAllBooks();
        assertThat(actualList).containsExactlyElementsOf(expectedList);
    }

    @DisplayName("UpdateBook")
    @Test
    public void shouldUpdateBook() {
        Book expectedBook = new Book(1, "TestBook", 2, 2);
        libraryDao.updateBook(expectedBook.getId(), expectedBook);
        Book updatedBook = libraryDao.getBookById(expectedBook.getId()).get();
        assertThat(updatedBook).usingRecursiveComparison().isEqualTo(expectedBook);
    }

    @DisplayName("Delete Book")
    @Test
    public void shouldDeleteBook() {
        long bookToDeleteId = 2;
        Optional<Book> bookBeforeDelete = libraryDao.getBookById(bookToDeleteId);
        libraryDao.deleteBook(bookToDeleteId);
        Optional<Book> bookAfterDelete = libraryDao.getBookById(bookToDeleteId);
        assertThat(bookAfterDelete).isEmpty();
    }

    @DisplayName("GetAuthorNameById")
    @Test
    public void shouldGetAuthorNameById() {
        String expectedName = "Robert Lewis Stevenson";
        String actualName = libraryDao.getAuthorById(1);
        assertThat(actualName).isEqualTo(expectedName);
    }

    @DisplayName("GetGenreById")
    @Test
    public void shouldGetGenreById() {
        String expectedGenre = "Novel";
        String actualGenre = libraryDao.getGenreById(1);
        assertThat(actualGenre).isEqualTo(expectedGenre);
    }

    @Test
    @DisplayName("GetAllAuthors")
    public void shouldGetAllAuthors() {
        List<Author> expectedList = List.of(
                new Author(1, "Robert Lewis Stevenson"),
                new Author(2, "Lev Tolstoi"),
                new Author(3, "Alexander Pushkin")
        );

        List<Author> actualList = libraryDao.getAllAuthors();
        assertThat(actualList).containsExactlyElementsOf(expectedList);
    }

    @Test
    @DisplayName("GetAllGenres")
    public void shouldGetAllGenres() {
        List<Genre> expectedList = List.of(
                new Genre(1, "Novel"),
                new Genre(2, "Fairy Tale"),
                new Genre(3, "Detective"),
                new Genre(4, "Adventure"),
                new Genre(5, "Horror")
        );

        List<Genre> actualList = libraryDao.getAllGenres();
        assertThat(actualList).containsExactlyElementsOf(expectedList);
    }
}
