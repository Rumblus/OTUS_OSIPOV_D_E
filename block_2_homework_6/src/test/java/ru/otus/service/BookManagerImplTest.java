package ru.otus.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import ru.otus.dao.AuthorDaoJpa;
import ru.otus.dao.BookDaoJpa;
import ru.otus.dao.GenreDaoJpa;
import ru.otus.domain.Author;
import ru.otus.domain.Book;
import ru.otus.domain.Genre;
import ru.otus.errors.LibraryErrorCode;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@DisplayName("Тесты сервисного класса библиотеки BookManagerImpl")
@DataJpaTest(properties = "spring.shell.interactive.enabled=false")
@ExtendWith(SpringExtension.class)
@Import(BookManagerImpl.class)
public class BookManagerImplTest {

    @Autowired
    private BookManagerImpl bookManager;

    @MockBean
    private BookDaoJpa bookDao;

    @MockBean
    private AuthorDaoJpa authorDao;

    @MockBean
    private GenreDaoJpa genreDao;

    @DisplayName("createBook")
    @Test
    public void shouldCreateBook() {
        Author author = new Author(2, "Lev Tolstoi");
        Genre genre = new Genre(2, "Fairy Tale");
        Book expectedBook = new Book(2, "TestBook", author, genre, new ArrayList<>());

        when(authorDao.getAuthorByName(author.getName())).thenReturn(author);
        when(genreDao.getGenreByName(genre.getName())).thenReturn(genre);
        when(bookDao.insertBook(any(Book.class))).thenReturn(expectedBook);

        Book actualBook = bookManager.createBook(expectedBook.getTitle(), expectedBook.getAuthor().getName(), expectedBook.getGenre().getName());
        assertThat(actualBook).usingRecursiveComparison().isEqualTo(expectedBook);
    }

    @DisplayName("getBookById")
    @Test
    public void shouldGetBookById() {
        Author author = new Author(2, "Lev Tolstoi");
        Genre genre = new Genre(2, "Detective");
        Book expectedBook = new Book(2, "Sherlock Holmes", author, genre, new ArrayList<>());

        when(bookDao.getBookById(any(long.class))).thenReturn(Optional.of(expectedBook));

        Book actualBook = bookManager.getBookById(expectedBook.getId());

        assertThat(actualBook).isNotNull();
        assertThat(actualBook).usingRecursiveComparison().isEqualTo(expectedBook);
    }

    @DisplayName("getAllBooks")
    @Test
    public void shouldGetAllBooks() {
        List<Book> expextedList = List.of(
                new Book(1, "Black Arrow", new Author(1, "Robert Lewis Stevenson"), new Genre(1, "Novel"), new ArrayList<>()),
                new Book(2, "Sherlock Holmes", new Author(2, "Lev Tolstoi"), new Genre(2, "Detective"), new ArrayList<>())
        );

        when(bookDao.getAllBooks()).thenReturn(expextedList);

        List<Book> actualList = bookManager.getAllBooks();
        assertThat(actualList).containsExactlyElementsOf(expextedList);
    }

    @DisplayName("updateBook")
    @Test
    public void shouldUpdateBook() {
        when(bookDao.getBookById(1)).thenReturn(Optional.of(new Book(2, "Sherlock Holmes", new Author(2, "Lev Tolstoi"), new Genre(2, "Detective"), new ArrayList<>())));
        when(authorDao.getAuthorByName("Robert Lewis Stevenson")).thenReturn(new Author(2, "Robert Lewis Stevenson"));
        when(genreDao.getGenreByName("Fairy Tale")).thenReturn(new Genre(2, "Fairy Tale"));

        LibraryErrorCode ec = bookManager.updateBook(1, "Black Arrow", "Robert Lewis Stevenson", "Fairy Tale");

        assertThat(ec).isEqualTo(LibraryErrorCode.ERR_OK);
    }

    @DisplayName("deleteBook")
    @Test
    public void shouldDeleteBook() {
        when(bookDao.getBookById(2)).thenReturn(Optional.of(new Book(2, "Sherlock Holmes", new Author(2, "Lev Tolstoi"), new Genre(2, "Detective"), new ArrayList<>())));
        when(authorDao.getAuthorByName("Robert Lewis Stevenson")).thenReturn(new Author(2, "Robert Lewis Stevenson"));
        when(genreDao.getGenreByName("Fairy Tale")).thenReturn(new Genre(2, "Fairy Tale"));

        LibraryErrorCode ec = bookManager.deleteBook(2);

        assertThat(ec).isEqualTo(LibraryErrorCode.ERR_OK);
    }
}
