package ru.otus.dao;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import ru.otus.domain.Author;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;

@DisplayName("Тесты Dao класса для авторов")
@ExtendWith(SpringExtension.class)
@DataMongoTest(properties = "spring.shell.interactive.enabled=false")
public class AuthorDaoTest {
    @Autowired
    private AuthorDao authorDao;

    @Test
    @DisplayName("GetAllAuthors")
    public void shouldGetAllAuthors() {
        List<Author> expectedList = List.of(
                new Author("Robert Lewis Stevenson"),
                new Author("Lev Tolstoi"),
                new Author("Alexander Pushkin")
        );

        List<Author> actualList = authorDao.findAll();
        for (Author author : actualList) {
            author.setId(null);
        }
        assertThat(actualList).containsExactlyElementsOf(expectedList);
    }

    @Test
    @DisplayName("getAuthorByName")
    public void shouldGetAuthorByName() {
        Author expectedAuthor = new Author("Robert Lewis Stevenson");
        Optional<Author> actualAuthor = authorDao.findByName(expectedAuthor.getName());
        assertThat(actualAuthor).isNotEmpty();
    }
}
