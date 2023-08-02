package ru.otus.dao;

import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.stereotype.Repository;
import ru.otus.domain.Author;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@Repository
public class AuthorDaoJdbc implements AuthorDao {
    private final NamedParameterJdbcOperations jdbc;

    @Override
    public Author getAuthorById(long id) {
        try {
            return jdbc.queryForObject("select id, author from AUTHORS where id = :id",
                    Map.of("id", id),
                    new AuthorMapper());
        } catch (DataAccessException e) {
            return null;
        }
    }

    @Override
    public Author getAuthorByName(String authorName) {
        try {
            return jdbc.queryForObject("select id, author from AUTHORS where author = :authorName",
                    Map.of("authorName", authorName),
                    new AuthorMapper());
        } catch (DataAccessException e) {
            return null;
        }
    }

    @Override
    public List<Author> getAllAuthors() {
        return jdbc.query("select id, author from AUTHORS", new AuthorMapper());
    }

    private static class AuthorMapper implements RowMapper<Author> {
        @Override
        public Author mapRow(ResultSet resultSet, int i) throws SQLException {
            long id = resultSet.getLong("id");
            String name = resultSet.getString("author");
            return new Author(id, name);
        }
    }
}
