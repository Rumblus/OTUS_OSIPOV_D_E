package ru.otus.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import ru.otus.dao.AuthorDaoJpa;
import ru.otus.domain.Author;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@DisplayName("Тесты сервисного класса библиотеки AuthorManagerImpl")
@DataJpaTest(properties = "spring.shell.interactive.enabled=false")
@ExtendWith(SpringExtension.class)
@Import(AuthorManagerImpl.class)
public class AuthorManagerImplTest {
    @Autowired
    private AuthorManagerImpl authorManager;

    @MockBean
    private AuthorDaoJpa authorDao;

    @Test
    @DisplayName("GetAllAuthors")
    public void shouldGetAllAuthors() {

        List<Author> expectedList = List.of(
                new Author(1, "Robert Lewis Stevenson"),
                new Author(2, "Lev Tolstoi"),
                new Author(3, "Alexander Pushkin")
        );
        when(authorDao.getAllAuthors()).thenReturn(expectedList);

        List<Author> actualList = authorManager.getAllAuthors();

        assertThat(actualList).containsExactlyElementsOf(expectedList);
    }
}
