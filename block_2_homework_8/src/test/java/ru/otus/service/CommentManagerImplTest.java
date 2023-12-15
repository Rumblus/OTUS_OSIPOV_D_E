package ru.otus.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import ru.otus.dao.BookDao;
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

@DisplayName("Тесты сервисного класса библиотеки CommentManagerImpl")
@DataMongoTest(properties = {"spring.shell.interactive.enabled=false", "mongock.enabled=false"})
@ExtendWith(SpringExtension.class)
@Import(CommentManagerImpl.class)
public class CommentManagerImplTest {

    @Autowired
    private CommentManagerImpl commentManager;

    @MockBean
    private BookDao bookDao;

    @Test
    @DisplayName("CreateComment")
    public void shouldCreateComment() {
        Mockito.doReturn(new Book("1","Black Arrow", new Author("Robert Lewis Stevenson"), new Genre("Novel"), List.of(new Comment("1", "Test comment")))).when(bookDao).save(any(Book.class));
        Mockito.doReturn(Optional.ofNullable(new Book("1","Black Arrow", new Author("Robert Lewis Stevenson"), new Genre("Novel"), new ArrayList<>()))).when(bookDao).findByTitle("Black Arrow");

        LibraryErrorCode err = commentManager.createComment("Black Arrow", "Test comment");
        assertThat(err).isEqualTo(LibraryErrorCode.ERR_OK);
    }

    @Test
    @DisplayName("DeleteComment")
    public void shouldDeleteComment() {
        Mockito.doReturn(Optional.ofNullable(new Book("1", "Black Arrow", new Author("Robert Lewis Stevenson"), new Genre("Novel"), new ArrayList<Comment>(List.of(new Comment("1", "Test comment")))))).when(bookDao).findByTitle("Black Arrow");
        LibraryErrorCode err = commentManager.deleteComment("Black Arrow", 1);
        assertThat(err).isEqualTo(LibraryErrorCode.ERR_OK);
    }
}
