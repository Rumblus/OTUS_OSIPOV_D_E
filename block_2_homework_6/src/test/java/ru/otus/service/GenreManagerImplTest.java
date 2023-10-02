package ru.otus.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import ru.otus.dao.GenreDaoJpa;
import ru.otus.domain.Genre;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@DisplayName("Тесты сервисного класса библиотеки GenreManagerImpl")
@DataJpaTest(properties = "spring.shell.interactive.enabled=false")
@ExtendWith(SpringExtension.class)
@Import(GenreManagerImpl.class)
public class GenreManagerImplTest {
    @Autowired
    private GenreManagerImpl genreManager;

    @MockBean
    private GenreDaoJpa genreDao;

    @DisplayName("GetAllGenres")
    @Test
    public void shouldGetAllGenres() {

        List<Genre> expectedList = List.of(
                new Genre(1, "Novel"),
                new Genre(2, "Fairy Tale"),
                new Genre(3, "Detective"),
                new Genre(4, "Adventure"),
                new Genre(5, "Horror")
        );
        when(genreDao.getAllGenres()).thenReturn(expectedList);

        List<Genre> actualList = genreManager.getAllGenres();

        assertThat(actualList).containsExactlyElementsOf(expectedList);
    }
}
