package ru.otus.dao;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import ru.otus.domain.Author;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;

@DisplayName("Тесты Dao класса для авторов")
@ExtendWith(SpringExtension.class)
@DataJpaTest(properties = "spring.shell.interactive.enabled=false")
public class AuthorDaoTest {
    @Autowired
    private AuthorDao authorDao;

    @Autowired
    private TestEntityManager em;

    @Test
    @DisplayName("GetAuthorById")
    public void shouldGetAuthorById() {
        Author expectedAuthor = em.find(Author.class, 1);
        Optional<Author> actualAuthor = authorDao.findById((long)1);
        assertThat(actualAuthor).isNotEmpty();
        assertThat(actualAuthor.get()).isEqualTo(expectedAuthor);
    }

    @Test
    @DisplayName("GetAllAuthors")
    public void shouldGetAllAuthors() {
        List<Author> expectedList = List.of(
                new Author(1, "Robert Lewis Stevenson"),
                new Author(2, "Lev Tolstoi"),
                new Author(3, "Alexander Pushkin")
        );

        List<Author> actualList = authorDao.findAll();
        assertThat(actualList).containsExactlyElementsOf(expectedList);
    }

    @Test
    @DisplayName("getAuthorByName")
    public void shouldGetAuthorByName() {
        Author expectedAuthor = new Author(1, "Robert Lewis Stevenson");
        Optional<Author> actualAuthor = authorDao.findByName(expectedAuthor.getName());
        assertThat(actualAuthor).isNotEmpty();
        assertThat(actualAuthor.get()).isEqualTo(expectedAuthor);
    }
}
