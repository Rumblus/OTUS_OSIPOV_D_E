package ru.otus.service;

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
import ru.otus.domain.Comment;
import ru.otus.domain.Genre;
import java.util.ArrayList;

import static org.assertj.core.api.Assertions.*;

@DisplayName("Интеграционные тесты сервисного класса библиотеки")
@DataJpaTest(properties = "spring.shell.interactive.enabled=false")
@ExtendWith(SpringExtension.class)
@Import({LibraryManagerImpl.class, BookManagerImpl.class, AuthorManagerImpl.class, GenreManagerImpl.class, CommentManagerImpl.class})
public class LibraryManagerImplTest {

    @Autowired
    private LibraryManagerImpl libraryManager;

    @Autowired
    private TestEntityManager em;

    @DisplayName("Должен добавлять книгу в библиотеку")
    @Test
    public void shouldCreateBook() {
        Book book = new Book(3, "Test Book", new Author(2, "Lev Tolstoi"), new Genre(2, "Fairy Tale"), new ArrayList<>());
        libraryManager.createBook(book.getTitle(), book.getAuthor().getName(), book.getGenre().getName());

        Book createdBook = em.find(Book.class, book.getId());

        assertThat(createdBook).usingRecursiveComparison().isEqualTo(book);
    }

    @DisplayName("Должен возвращать книгу по ID")
    @Test
    public void shouldGetBookById() {
        Book expectedBook = em.find(Book.class, 1);
        Book actualBook = libraryManager.getBookById(expectedBook.getId());

        assertThat(actualBook).usingRecursiveComparison().isEqualTo(expectedBook);
    }

    @DisplayName("Должен редактировать книгу по ID")
    @Test
    public void shouldUpdateBook() {
        Book expectedBook = em.find(Book.class, 1);
        libraryManager.updateBook(expectedBook.getId(), expectedBook.getTitle(), expectedBook.getAuthor().getName(), expectedBook.getGenre().getName());

        Book actualBook = libraryManager.getBookById(expectedBook.getId());

        assertThat(actualBook).usingRecursiveComparison().isEqualTo(expectedBook);
    }

    @DisplayName("Должен удалять книгу")
    @Test
    public void shouldDeleteBook() {
        long idToDelete = 2;
        libraryManager.deleteBook(idToDelete);

        Book actualBook = em.find(Book.class, idToDelete);

        assertThat(actualBook).isNull();
    }

    @DisplayName("Должен создавать комментарий")
    @Test
    public void shouldCreateComment() {
        Book book = em.find(Book.class, 1);
        Comment commentToCreate = new Comment(2, book, "Test comment");
        libraryManager.createComment(book.getTitle(), commentToCreate.getData());

        Comment actualComment = em.find(Comment.class, commentToCreate.getId());

        assertThat(actualComment).isEqualTo(commentToCreate);
    }

    @DisplayName("Должен удалять комментарий")
    @Test
    public void shouldDeleteComment() {
        Comment commentToDelete = em.find(Comment.class, 1);
        libraryManager.deleteComment(commentToDelete.getBook().getTitle(), (int) commentToDelete.getId());

        Comment selectedComment = em.find(Comment.class, commentToDelete.getId());

        assertThat(selectedComment).isNull();
    }
}
