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
import ru.otus.domain.Book;
import ru.otus.domain.Comment;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;

@DisplayName("Тесты Dao для комментариев")
@ExtendWith(SpringExtension.class)
@DataJpaTest(properties = "spring.shell.interactive.enabled=false")
@Import(CommentDaoJpa.class)
public class CommentDaoJpaTest {
    @Autowired
    private CommentDaoJpa commentDao;

    @Autowired
    private TestEntityManager em;

    @DisplayName("GetById")
    @Test
    public void shouldGetById() {
        Comment expectedComment = em.find(Comment.class, 1);
        Optional<Comment> actualComment = commentDao.getCommentById(1);

        assertThat(actualComment).isNotEmpty();
        assertThat(actualComment.get()).isEqualTo(expectedComment);
    }

    @DisplayName("GetAllCommentsByBookId")
    @Test
    public void shouldGetAllCommentsByBookId() {
        TypedQuery<Comment> query = em.getEntityManager().createQuery(
                "select c from Comment c where c.book.id = 1", Comment.class);
        List<Comment> expectedList = query.getResultList();

        List<Comment> actualList = commentDao.getAllCommentsByBookId(1);

        assertThat(actualList).containsExactlyElementsOf(expectedList);
    }

    @DisplayName("CreateComment")
    @Test
    public void shouldCreateComment() {
        Comment commentToCreate = new Comment(2, em.find(Book.class, 1), "Test comment");
        commentDao.createComment(commentToCreate);

        Comment selectedComment = em.find(Comment.class, 2);

        assertThat(selectedComment).isNotNull();
        assertThat(selectedComment).isEqualTo(commentToCreate);
    }

    @DisplayName("CreateComment")
    @Test
    public void shouldDeleteComment() {
        Comment commentToDelete = em.find(Comment.class, 1);
        commentDao.deleteComment(commentToDelete);

        Comment selectedComment = em.find(Comment.class, 1);

        assertThat(selectedComment).isNull();
    }
}
