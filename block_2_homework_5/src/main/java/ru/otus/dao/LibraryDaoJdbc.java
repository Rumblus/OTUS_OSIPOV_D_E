package ru.otus.dao;

import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import ru.otus.domain.Author;
import ru.otus.domain.Book;
import ru.otus.domain.Genre;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Repository
public class LibraryDaoJdbc implements LibraryDao {
    private final NamedParameterJdbcOperations jdbc;

    @Override
    public Book insertBook(Book book) {
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("title", book.getTitle());
        params.addValue("authorId", book.getAuthorId());
        params.addValue("genreId", book.getGenreId());
        KeyHolder kh = new GeneratedKeyHolder();

        jdbc.update("insert into BOOKS(title, author_id, genre_id) values(:title, :authorId, :genreId)",
                params, kh, new String[]{"id"});

        book.setId(kh.getKey().longValue());
        return book;
    }

    @Override
    public Optional<Book> getBookById(long id) {
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("id", id);
        List<Book> bookList = jdbc.query("select * from BOOKS where id = :id", params, new BookMapper());
        if (!bookList.isEmpty()) {
            return Optional.of(bookList.get(0));
        } else {
            return Optional.ofNullable(null);
        }
    }

    @Override
    public List<Book> getAllBooks() {
        return jdbc.query("select * from BOOKS", new BookMapper());
    }

    @Override
    public void updateBook(long id, Book book) {
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("title", book.getTitle());
        params.addValue("authorId", book.getAuthorId());
        params.addValue("genreId", book.getGenreId());
        params.addValue("id", id);
        jdbc.update("update BOOKS " +
                        "set title = :title, author_id = :authorId, genre_id = :genreId " +
                        "where id = :id",
                        params);
    }

    @Override
    public void deleteBook(long id) {
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("id", id);
        jdbc.update("delete from BOOKS where id = :id", params);
    }

    @Override
    public String getAuthorById(long id) {
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("id", id);

        try {
            return jdbc.queryForObject("select * from AUTHORS where id = :id", params, new AuthorMapper()).getName();
        } catch (DataAccessException e) {
            return null;
        }
    }

    @Override
    public String getGenreById(long id) {
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("id", id);

        try {
            return jdbc.queryForObject("select * from GENRES where id = :id", params, new GenreMapper()).getName();
        } catch (DataAccessException e) {
            return null;
        }
    }

    @Override
    public List<Author> getAllAuthors() {
        return jdbc.query("select * from AUTHORS", new AuthorMapper());
    }

    @Override
    public List<Genre> getAllGenres() {
        return jdbc.query("select * from GENRES", new GenreMapper());
    }

    private static class BookMapper implements RowMapper<Book> {
        @Override
        public Book mapRow(ResultSet resultSet, int i) throws SQLException {
            if (resultSet == null) {
                return null;
            }

            long id = resultSet.getLong("id");
            String title = resultSet.getString("title");
            long authorId = resultSet.getLong("author_id");
            long genreId = resultSet.getLong("genre_id");
            return new Book(id, title, authorId, genreId);
        }
    }

    private static class AuthorMapper implements RowMapper<Author> {
        @Override
        public Author mapRow(ResultSet resultSet, int i) throws SQLException {
            long id = resultSet.getLong("id");
            String name = resultSet.getString("author");
            return new Author(id, name);
        }
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
