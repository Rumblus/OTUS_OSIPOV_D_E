package ru.otus.service;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.test.context.junit.jupiter.SpringExtension;
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

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@DisplayName("Тесты сервисного класса библиотеки BookManagerImpl")
@DataMongoTest(properties = {"spring.shell.interactive.enabled=false", "mongock.enabled=false"})
@ExtendWith(SpringExtension.class)
@Import(BookManagerImpl.class)
public class BookManagerImplTest {

    @Autowired
    private BookManagerImpl bookManager;

    @Autowired
    private MongoTemplate mongoTemplate;

    @MockBean
    private BookDao bookDao;

    @MockBean
    private AuthorDao authorDao;

    @MockBean
    private GenreDao genreDao;

    @BeforeEach
    public void fillDb() {
        mongoTemplate.save(new Author("1","Robert Lewis Stevenson"));
        mongoTemplate.save(new Genre("1","Novel"));
        mongoTemplate.save(
                new Book("1",
                        "Black Arrow",
                        new Author("1","Robert Lewis Stevenson"),
                        new Genre("1","Novel"),
                        List.of(new Comment("Very interesting book!")))
        );
    }

    @DisplayName("createBook")
    @Test
    public void shouldCreateBook() {
        Author author = new Author("Lev Tolstoi");
        Genre genre = new Genre("Fairy Tale");
        Book expectedBook = new Book("TestBook", author, genre, new ArrayList<>());

        when(authorDao.findByName(author.getName())).thenReturn(Optional.of(author));
        when(genreDao.findByName(genre.getName())).thenReturn(Optional.of(genre));
        when(bookDao.save(any(Book.class))).thenReturn(expectedBook);

        Book actualBook = bookManager.createBook(expectedBook.getTitle(), expectedBook.getAuthor().getName(), expectedBook.getGenre().getName());
        assertThat(actualBook).usingRecursiveComparison().isEqualTo(expectedBook);
    }

    @DisplayName("getBookById")
    @Test
    public void shouldGetBookById() {

        Book bookThatExists = mongoTemplate.findAll(Book.class).get(0);

        Author author = new Author("Robert Lewis Stevenson");
        Genre genre = new Genre("Novel");
        Book expectedBook = new Book(bookThatExists.getId(), "Black Arrow", author, genre, new ArrayList<>());

        when(bookDao.findById(any(String.class))).thenReturn(Optional.of(expectedBook));

        Book actualBook = bookManager.getBookById(expectedBook.getId());

        assertThat(actualBook).isNotNull();
        assertThat(actualBook).usingRecursiveComparison().isEqualTo(expectedBook);
    }

    @DisplayName("getAllBooks")
    @Test
    public void shouldGetAllBooks() {
        List<Book> expextedList = List.of(
                new Book("Black Arrow", new Author("Robert Lewis Stevenson"), new Genre("Novel"), new ArrayList<>()),
                new Book("Sherlock Holmes", new Author("Lev Tolstoi"), new Genre("Detective"), new ArrayList<>())
        );

        when(bookDao.findAll()).thenReturn(expextedList);

        List<Book> actualList = bookManager.getAllBooks();
        assertThat(actualList).containsExactlyElementsOf(expextedList);
    }

    @DisplayName("updateBook")
    @Test
    public void shouldUpdateBook() {
        when(bookDao.findById(any(String.class))).thenReturn(Optional.of(new Book("Sherlock Holmes", new Author("Lev Tolstoi"), new Genre("Detective"), new ArrayList<>())));
        when(authorDao.findByName("Robert Lewis Stevenson")).thenReturn(Optional.of(new Author("Robert Lewis Stevenson")));
        when(genreDao.findByName("Fairy Tale")).thenReturn(Optional.of(new Genre("Fairy Tale")));

        LibraryErrorCode ec = bookManager.updateBook("1","Black Arrow", "Robert Lewis Stevenson", "Fairy Tale");

        assertThat(ec).isEqualTo(LibraryErrorCode.ERR_OK);
    }

    @DisplayName("deleteBook")
    @Test
    public void shouldDeleteBook() {
        when(bookDao.findById(any(String.class))).thenReturn(Optional.of(new Book("Sherlock Holmes", new Author("Lev Tolstoi"), new Genre("Detective"), new ArrayList<>())));
        when(authorDao.findByName("Robert Lewis Stevenson")).thenReturn(Optional.of(new Author("Robert Lewis Stevenson")));
        when(genreDao.findByName("Fairy Tale")).thenReturn(Optional.of(new Genre("Fairy Tale")));

        LibraryErrorCode ec = bookManager.deleteBook("2");

        assertThat(ec).isEqualTo(LibraryErrorCode.ERR_OK);
    }
}
