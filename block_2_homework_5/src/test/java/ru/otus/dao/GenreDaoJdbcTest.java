package ru.otus.dao;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import ru.otus.domain.Genre;

import java.util.List;

import static org.assertj.core.api.Assertions.*;

@DisplayName("Тесты Dao класса для жанров")
@ExtendWith(SpringExtension.class)
@JdbcTest(properties = "spring.shell.interactive.enabled=false")
@Import(GenreDaoJdbc.class)
public class GenreDaoJdbcTest {
    @Autowired
    private GenreDaoJdbc genreDao;

    @Test
    @DisplayName("GetGenreById")
    public void shouldGetGenreById() {
        Genre expectedGenre = new Genre(1, "Novel");
        Genre actualGenre = genreDao.getGenreById(1);
        assertThat(actualGenre).isEqualTo(expectedGenre);
    }

    @Test
    @DisplayName("GetAllGernes")
    public void shouldGetAllGenres() {
        List<Genre> expectedList = List.of(
                new Genre(1, "Novel"),
                new Genre(2, "Fairy Tale"),
                new Genre(3, "Detective"),
                new Genre(4, "Adventure"),
                new Genre(5, "Horror")
        );

        List<Genre> actualList = genreDao.getAllGenres();
        assertThat(actualList).containsExactlyElementsOf(expectedList);
    }

    @Test
    @DisplayName("getGenreByName")
    public void shouldGetGenreByName() {
        Genre expectedGenre = new Genre(1, "Novel");
        Genre actualGenre = genreDao.getGenreByName(expectedGenre.getName());
        assertThat(actualGenre).isEqualTo(expectedGenre);
    }
}
