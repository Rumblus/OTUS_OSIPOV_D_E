package ru.otus.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import ru.otus.dao.AuthorDao;
import ru.otus.domain.Author;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@DisplayName("Тесты сервисного класса библиотеки AuthorManagerImpl")
@DataMongoTest(properties = {"spring.shell.interactive.enabled=false", "mongock.enabled=false"})
@ExtendWith(SpringExtension.class)
@Import(AuthorManagerImpl.class)
public class AuthorManagerImplTest {
    @Autowired
    private AuthorManagerImpl authorManager;

    @MockBean
    private AuthorDao authorDao;

    @Test
    @DisplayName("GetAllAuthors")
    public void shouldGetAllAuthors() {

        List<Author> expectedList = List.of(
                new Author("Robert Lewis Stevenson"),
                new Author("Lev Tolstoi"),
                new Author("Alexander Pushkin")
        );
        when(authorDao.findAll()).thenReturn(expectedList);

        List<Author> actualList = authorManager.getAllAuthors();

        assertThat(actualList).containsExactlyElementsOf(expectedList);
    }
}
