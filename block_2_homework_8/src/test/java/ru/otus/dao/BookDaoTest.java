package ru.otus.dao;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import ru.otus.domain.Author;
import ru.otus.domain.Book;
import ru.otus.domain.Genre;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;

@DisplayName("Тесты Dao класса для книг")
@ExtendWith(SpringExtension.class)
@DataMongoTest(properties = "spring.shell.interactive.enabled=false")
public class BookDaoTest {
    @Autowired
    private BookDao bookDao;

    @Autowired
    private MongoTemplate mongoTemplate;

    @DisplayName("InsertBook")
    @Test
    public void shouldInsertBook() {
        Author author = new Author("1","Lev Tolstoi");
        Genre genre = new Genre("1","Fairy Tale");
        Book expectedBook = new Book("1","TestBook1", author, genre, new ArrayList<>());
        Book insertedBook = bookDao.save(expectedBook);
        assertThat(insertedBook).usingRecursiveComparison().isEqualTo(expectedBook);
    }

    @DisplayName("GetById")
    @Test
    public void shouldGetBookById() {
        Book bookThatExists = mongoTemplate.findAll(Book.class).get(0);
        Book selectedBook = bookDao.findById(bookThatExists.getId()).get();
        assertThat(selectedBook).usingRecursiveComparison().isEqualTo(bookThatExists);
    }

    @DisplayName("GetByName")
    @Test
    public void shouldGetBookByName() {
        Query query = new Query(Criteria.where("title").in("Black Arrow","TestBook")).limit(1);
        List<Book> books = mongoTemplate.findAll(Book.class);
        Book bookThatExists = mongoTemplate.find(query, Book.class).get(0);
        Optional<Book> selectedBook = bookDao.findByTitle(bookThatExists.getTitle());
        assertThat(selectedBook).isNotEmpty();
        assertThat(selectedBook.get()).usingRecursiveComparison().isEqualTo(bookThatExists);
    }

    @DisplayName("GetAllBooks")
    @Test
    public void shouldGetAllBooks() {
        List<Book> expectedList = mongoTemplate.findAll(Book.class);

        List<Book> actualList = bookDao.findAll();

        int i = 0;
        for (Book book : actualList)
            assertThat(book).usingRecursiveComparison().isEqualTo(expectedList.get(i++));
    }

    @DisplayName("UpdateBook")
    @Test
    public void shouldUpdateBook() {
        Book bookThatExists = mongoTemplate.findOne(Query.query(Criteria.where("title").is("Black Arrow")), Book.class);
        Book expectedBook = new Book(bookThatExists.getId(), "TestBook", new Author("2", "Lev Tolstoi"), new Genre("2", "Fairy Tale"), new ArrayList<>());
        Book updatedBook = bookDao.save(expectedBook);
        assertThat(updatedBook).usingRecursiveComparison().isEqualTo(expectedBook);
    }

    @DisplayName("Delete Book")
    @Test
    public void shouldDeleteBook() {
        Book bookToDelete = mongoTemplate.findOne(Query.query(Criteria.where("title").in("Black Arrow","TestBook")).limit(1), Book.class);
        bookDao.delete(bookToDelete);
        Book bookAfterDelete = mongoTemplate.findById(bookToDelete.getId(), Book.class);
        assertThat(bookAfterDelete).isNull();
    }
}
