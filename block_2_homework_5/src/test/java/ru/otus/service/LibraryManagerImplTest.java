package ru.otus.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import ru.otus.dao.LibraryDaoJdbc;
import ru.otus.domain.Author;
import ru.otus.domain.Book;
import ru.otus.domain.Genre;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.assertj.core.api.Assertions.*;

@DisplayName("Тесты сервисного класса библиотеки")
@JdbcTest(properties = "spring.shell.interactive.enabled=false")
@ExtendWith(SpringExtension.class)
@Import({LibraryManagerImpl.class, LibraryDaoJdbc.class})
public class LibraryManagerImplTest {
    @Autowired
    private LibraryDaoJdbc libraryDao;
    @Autowired
    private LibraryManagerImpl libraryManager;

    @DisplayName("Должен добавлять книгу в библиотеку")
    @Test
    public void shouldCreateBook() {
        Book book = new Book(3, "Test Book", new Author(2, "Lev Tolstoi"), new Genre(2, "Fairy Tale"));
        libraryManager.createBook(book.getTitle(), book.getAuthor().getName(), book.getGenre().getName());

        PrintStream systemOutput = System.out;
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        PrintStream out = new PrintStream(baos);
        System.setOut(out);

        libraryManager.getBookById(book.getId());

        String expectedString = new String("Book\n\tid: " + book.getId() +
                "\n\ttitle: " + book.getTitle() +
                "\n\tauthor: " + "Lev Tolstoi" +
                "\n\tgenre: " + "Fairy Tale");

        String[] lines = baos.toString().split(System.lineSeparator());
        System.setOut(systemOutput);
        assert(lines[0]).contains(expectedString.subSequence(0, expectedString.length() - 1));
    }

    @DisplayName("Должен возвращать книгу по ID")
    @Test
    public void shouldGetBookById() {
        PrintStream systemOutput = System.out;
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        PrintStream out = new PrintStream(baos);
        System.setOut(out);

        Book book = new Book(1, "Black Arrow", new Author(1, "Robert Lewis Stevenson"), new Genre(1, "Novel"));
        libraryManager.getBookById(book.getId());

        String expectedString = new String(
                "Book\n\tid: " + book.getId() +
                "\n\ttitle: " + book.getTitle() +
                "\n\tauthor: " + "Robert Lewis Stevenson" +
                "\n\tgenre: " + "Novel");

        String[] lines = baos.toString().split(System.lineSeparator());
        System.setOut(systemOutput);
        assert(lines[0]).contains(expectedString.subSequence(0, expectedString.length() - 1));
    }

    @DisplayName("Должен редактировать книгу по ID")
    @Test
    public void shouldUpdateBook() {
        Book book = new Book(1, "TestBook", new Author(2, "Lev Tolstoi"), new Genre(2, "Fairy Tale"));
        libraryManager.updateBook(book.getId(), book.getTitle(), book.getAuthor().getName(), book.getGenre().getName());

        PrintStream systemOutput = System.out;
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        PrintStream out = new PrintStream(baos);
        System.setOut(out);

        libraryManager.getBookById(book.getId());

        String expectedString = new String(
                "Book\n\tid: " + book.getId() +
                        "\n\ttitle: " + book.getTitle() +
                        "\n\tauthor: " + "Lev Tolstoi" +
                        "\n\tgenre: " + "Fairy Tale");

        String[] lines = baos.toString().split(System.lineSeparator());
        System.setOut(systemOutput);
        assert(lines[0]).contains(expectedString.subSequence(0, expectedString.length() - 1));
    }

    @DisplayName("Должен удалять книгу")
    @Test
    public void shouldDeleteBook() {
        long idToDelete = 2;
        libraryManager.deleteBook(idToDelete);

        PrintStream systemOutput = System.out;
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        PrintStream out = new PrintStream(baos);
        System.setOut(out);

        String expectedString = new String("No book with id = " + idToDelete + " found!");

        libraryManager.getBookById(idToDelete);

        String[] lines = baos.toString().split(System.lineSeparator());
        System.setOut(systemOutput);
        assert(lines[0]).contains(expectedString.subSequence(0, expectedString.length() - 1));
    }
}
