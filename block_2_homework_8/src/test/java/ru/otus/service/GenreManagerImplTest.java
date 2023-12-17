package ru.otus.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import ru.otus.dao.GenreDao;
import ru.otus.domain.Genre;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@DisplayName("Тесты сервисного класса библиотеки GenreManagerImpl")
@DataMongoTest(properties = {"spring.shell.interactive.enabled=false", "mongock.enabled=false"})
@ExtendWith(SpringExtension.class)
@Import(GenreManagerImpl.class)
public class GenreManagerImplTest {
    @Autowired
    private GenreManagerImpl genreManager;

    @MockBean
    private GenreDao genreDao;

    @DisplayName("GetAllGenres")
    @Test
    public void shouldGetAllGenres() {

        List<Genre> expectedList = List.of(
                new Genre("Novel"),
                new Genre("Fairy Tale"),
                new Genre("Detective"),
                new Genre("Adventure"),
                new Genre("Horror")
        );
        when(genreDao.findAll()).thenReturn(expectedList);

        List<Genre> actualList = genreManager.getAllGenres();

        assertThat(actualList).containsExactlyElementsOf(expectedList);
    }
}
