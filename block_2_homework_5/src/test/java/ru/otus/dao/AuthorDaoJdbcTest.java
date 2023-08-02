package ru.otus.dao;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import ru.otus.domain.Author;

import java.util.List;

import static org.assertj.core.api.Assertions.*;

@DisplayName("Тесты Dao класса для авторов")
@ExtendWith(SpringExtension.class)
@JdbcTest(properties = "spring.shell.interactive.enabled=false")
@Import(AuthorDaoJdbc.class)
public class AuthorDaoJdbcTest {
    @Autowired
    private AuthorDaoJdbc authorDao;

    @Test
    @DisplayName("GetAuthorById")
    public void shouldGetAuthorById() {
        Author expectedAuthor = new Author(1, "Robert Lewis Stevenson");
        Author actualAuthor = authorDao.getAuthorById(1);
        assertThat(actualAuthor).isEqualTo(expectedAuthor);
    }

    @Test
    @DisplayName("GetAllAuthors")
    public void shouldGetAllAuthors() {
        List<Author> expectedList = List.of(
                new Author(1, "Robert Lewis Stevenson"),
                new Author(2, "Lev Tolstoi"),
                new Author(3, "Alexander Pushkin")
        );

        List<Author> actualList = authorDao.getAllAuthors();
        assertThat(actualList).containsExactlyElementsOf(expectedList);
    }

    @Test
    @DisplayName("getAuthorByName")
    public void shouldGetAuthorByName() {
        Author expectedAuthor = new Author(1, "Robert Lewis Stevenson");
        Author actualAuthor = authorDao.getAuthorByName(expectedAuthor.getName());
        assertThat(actualAuthor).isEqualTo(expectedAuthor);
    }
}
