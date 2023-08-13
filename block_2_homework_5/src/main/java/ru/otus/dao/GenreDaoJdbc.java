package ru.otus.dao;

import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.stereotype.Repository;
import ru.otus.domain.Genre;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@Repository
public class GenreDaoJdbc implements GenreDao {
    private final NamedParameterJdbcOperations jdbc;

    @Override
    public Genre getGenreById(long id) {
        try {
            return jdbc.queryForObject("select id, genre from GENRES where id = :id",
                    Map.of("id", id),
                    new GenreMapper());
        } catch (DataAccessException e) {
            return null;
        }
    }

    @Override
    public Genre getGenreByName(String genreName) {
        try {
            return jdbc.queryForObject("select id, genre from GENRES where genre = :genreName",
                    Map.of("genreName", genreName),
                    new GenreMapper());
        } catch (DataAccessException e) {
            return null;
        }
    }

    @Override
    public List<Genre> getAllGenres() {
        return jdbc.query("select id, genre from GENRES", new GenreMapper());
    }

    private static class GenreMapper implements RowMapper<Genre> {
        @Override
        public Genre mapRow(ResultSet resultSet, int i) throws SQLException {
            long id = resultSet.getLong("id");
            String genre = resultSet.getString("genre");
            return new Genre(id, genre);
        }
    }
}
