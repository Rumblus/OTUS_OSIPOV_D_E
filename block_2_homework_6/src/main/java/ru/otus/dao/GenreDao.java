package ru.otus.dao;

import ru.otus.domain.Genre;
import java.util.List;

public interface GenreDao {
    public Genre getGenreById(long id);

    Genre getGenreByName(String genreName);

    List<Genre> getAllGenres();
}
