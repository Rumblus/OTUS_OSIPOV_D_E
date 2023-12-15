package ru.otus.dao;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import ru.otus.domain.Genre;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;

@DisplayName("Тесты Dao класса для жанров")
@ExtendWith(SpringExtension.class)
@DataMongoTest(properties = "spring.shell.interactive.enabled=false")
public class GenreDaoTest {
    @Autowired
    private GenreDao genreDao;

    @Test
    @DisplayName("GetAllGernes")
    public void shouldGetAllGenres() {
        List<Genre> expectedList = List.of(
                new Genre("Novel"),
                new Genre("Fairy Tale"),
                new Genre("Detective"),
                new Genre("Adventure"),
                new Genre("Horror")
        );

        List<Genre> actualList = genreDao.findAll();
        for (Genre genre : actualList) {
            genre.setId(null);
        }
        assertThat(actualList).containsExactlyElementsOf(expectedList);
    }

    @Test
    @DisplayName("getGenreByName")
    public void shouldGetGenreByName() {
        Genre expectedGenre = new Genre("Novel");
        Optional<Genre> actualGenre = genreDao.findByName(expectedGenre.getName());
        assertThat(actualGenre).isNotEmpty();
    }
}
