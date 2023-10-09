package ru.otus.dao;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import ru.otus.domain.Book;
import ru.otus.domain.Comment;

import java.util.Optional;

import static org.assertj.core.api.Assertions.*;

@DisplayName("Тесты Dao для комментариев")
@ExtendWith(SpringExtension.class)
@DataJpaTest(properties = "spring.shell.interactive.enabled=false")
public class CommentDaoTest {
    @Autowired
    private CommentDao commentDao;

    @Autowired
    private TestEntityManager em;

    @DisplayName("GetById")
    @Test
    public void shouldGetById() {
        Comment expectedComment = em.find(Comment.class, 1);
        Optional<Comment> actualComment = commentDao.findById((long)1);

        assertThat(actualComment).isNotEmpty();
        assertThat(actualComment.get()).isEqualTo(expectedComment);
    }

    @DisplayName("CreateComment")
    @Test
    public void shouldCreateComment() {
        Comment commentToCreate = new Comment(2, em.find(Book.class, 1), "Test comment");
        commentDao.save(commentToCreate);

        Comment selectedComment = em.find(Comment.class, 2);

        assertThat(selectedComment).isNotNull();
        assertThat(selectedComment).isEqualTo(commentToCreate);
    }

    @DisplayName("CreateComment")
    @Test
    public void shouldDeleteComment() {
        Comment commentToDelete = em.find(Comment.class, 1);
        commentDao.delete(commentToDelete);

        Comment selectedComment = em.find(Comment.class, 1);

        assertThat(selectedComment).isNull();
    }
}
