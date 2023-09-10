package ru.otus.dao;

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

import java.util.List;

import static org.assertj.core.api.Assertions.*;

@DisplayName("Тесты Dao класса для авторов")
@ExtendWith(SpringExtension.class)
@DataJpaTest(properties = "spring.shell.interactive.enabled=false")
@Import({AuthorDaoJpa.class, Author.class})
public class AuthorDaoJpaTest {
    @Autowired
    private AuthorDaoJpa authorDao;

    @Autowired
    private TestEntityManager em;

    @Test
    @DisplayName("GetAuthorById")
    public void shouldGetAuthorById() {
        Author expectedAuthor = em.find(Author.class, 1);
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
