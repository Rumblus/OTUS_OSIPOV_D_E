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

@DisplayName("Тесты Dao класса для книг")
@ExtendWith(SpringExtension.class)
@JdbcTest(properties = "spring.shell.interactive.enabled=false")
@Import(BookDaoJdbc.class)
public class BookDaoJdbcTest {
    @Autowired
    private BookDaoJdbc bookDao;

    @DisplayName("InsertBook")
    @Test
    public void shouldInsertBook() {
        Author author = new Author(2, "Lev Tolstoi");
        Genre genre = new Genre(2, "Fairy Tale");
        Book expectedBook = new Book(2, "TestBook", author, genre);
        Book insertedBook = bookDao.insertBook(expectedBook);
        Book selectedBook = bookDao.getBookById(insertedBook.getId()).get();
        assertThat(selectedBook).usingRecursiveComparison().isEqualTo(expectedBook);
    }

    @DisplayName("GetById")
    @Test
    public void shouldGetBookById() {
        Book bookThatExists = new Book(1, "Black Arrow", new Author(1, "Robert Lewis Stevenson"), new Genre(1, "Novel"));
        Book selectedBook = bookDao.getBookById(bookThatExists.getId()).get();
        assertThat(selectedBook).usingRecursiveComparison().isEqualTo(bookThatExists);
    }

    @Test
    @DisplayName("GetAllBooks")
    public void shouldGetAllBooks() {
        List<Book> expectedList = List.of(
                new Book(1, "Black Arrow", new Author(1, "Robert Lewis Stevenson"), new Genre(1, "Novel")),
                new Book(2, "Sherlock Holmes", new Author(2, "Lev Tolstoi"), new Genre(3, "Detective"))
        );

        List<Book> actualList = bookDao.getAllBooks();
        assertThat(actualList).containsExactlyElementsOf(expectedList);
    }

    @DisplayName("UpdateBook")
    @Test
    public void shouldUpdateBook() {
        Book expectedBook = new Book(1, "TestBook", new Author(2, "Lev Tolstoi"), new Genre(2, "Fairy Tale"));
        bookDao.updateBook(expectedBook.getId(), expectedBook);
        Book updatedBook = bookDao.getBookById(expectedBook.getId()).get();
        assertThat(updatedBook).usingRecursiveComparison().isEqualTo(expectedBook);
    }

    @DisplayName("Delete Book")
    @Test
    public void shouldDeleteBook() {
        long bookToDeleteId = 2;
        Optional<Book> bookBeforeDelete = bookDao.getBookById(bookToDeleteId);
        bookDao.deleteBook(bookToDeleteId);
        Optional<Book> bookAfterDelete = bookDao.getBookById(bookToDeleteId);
        assertThat(bookAfterDelete).isEmpty();
    }
}
