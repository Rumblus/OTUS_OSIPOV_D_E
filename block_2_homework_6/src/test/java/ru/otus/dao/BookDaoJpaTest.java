package ru.otus.dao;

import jakarta.persistence.TypedQuery;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;
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
@DataJpaTest(properties = "spring.shell.interactive.enabled=false")
@Import(BookDaoJpa.class)
public class BookDaoJpaTest {
    @Autowired
    private BookDaoJpa bookDao;

    @Autowired
    private TestEntityManager em;

    @DisplayName("InsertBook")
    @Test
    public void shouldInsertBook() {
        Author author = new Author(2, "Lev Tolstoi");
        Genre genre = new Genre(2, "Fairy Tale");
        Book expectedBook = new Book(2, "TestBook", author, genre, new ArrayList<>());
        Book insertedBook = bookDao.insertBook(expectedBook);
        Book selectedBook = em.find(Book.class, insertedBook.getId());
        assertThat(selectedBook).usingRecursiveComparison().isEqualTo(expectedBook);
    }

    @DisplayName("GetById")
    @Test
    public void shouldGetBookById() {
        Book bookThatExists = em.find(Book.class, 1);
        Book selectedBook = bookDao.getBookById(bookThatExists.getId()).get();
        assertThat(selectedBook).usingRecursiveComparison().isEqualTo(bookThatExists);
    }

    @DisplayName("GetByName")
    @Test
    public void shouldGetBookByName() {
        Book bookThatExists = em.find(Book.class, 1);
        Optional<Book> selectedBook = bookDao.getBookByName(bookThatExists.getTitle());
        assertThat(selectedBook).isNotEmpty();
        assertThat(selectedBook.get()).usingRecursiveComparison().isEqualTo(bookThatExists);
    }

    @Test
    @DisplayName("GetAllBooks")
    public void shouldGetAllBooks() {
        TypedQuery<Book> query = em.getEntityManager().createQuery("select b from Book b left join fetch b.author left join fetch b.genre left join fetch b.comments", Book.class);
        List<Book> expectedList = query.getResultList();

        List<Book> actualList = bookDao.getAllBooks();

        int i = 0;
        for (Book book : actualList)
            assertThat(book).usingRecursiveComparison().isEqualTo(expectedList.get(i++));
    }

    @DisplayName("UpdateBook")
    @Test
    public void shouldUpdateBook() {
        Book expectedBook = new Book(1, "TestBook", new Author(2, "Lev Tolstoi"), new Genre(2, "Fairy Tale"), new ArrayList<>());
        bookDao.updateBook(expectedBook);
        Book updatedBook = em.find(Book.class, expectedBook.getId());
        assertThat(updatedBook).usingRecursiveComparison().isEqualTo(expectedBook);
    }

    @DisplayName("Delete Book")
    @Test
    public void shouldDeleteBook() {
        long bookToDeleteId = 2;
        Book bookToDelete = em.find(Book.class, bookToDeleteId);
        bookDao.deleteBook(bookToDelete);
        Book bookAfterDelete = em.find(Book.class, bookToDeleteId);
        assertThat(bookAfterDelete).isNull();
    }
}
