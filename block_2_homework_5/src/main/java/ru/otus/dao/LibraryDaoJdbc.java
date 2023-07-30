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
import java.util.Map;
import java.util.Optional;

@RequiredArgsConstructor
@Repository
public class LibraryDaoJdbc implements LibraryDao {
    private final NamedParameterJdbcOperations jdbc;

    @Override
    public Book insertBook(Book book) {
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("title", book.getTitle());
        params.addValue("authorId", book.getAuthor().getId());
        params.addValue("genreId", book.getGenre().getId());
        KeyHolder kh = new GeneratedKeyHolder();

        jdbc.update("insert into BOOKS(title, author_id, genre_id) values(:title, :authorId, :genreId)",
                params, kh, new String[]{"id"});

        book.setId(kh.getKey().longValue());
        return book;
    }

    @Override
    public Optional<Book> getBookById(long id) {
        List<Book> bookList = jdbc.query("select id, title, author_id, genre_id from BOOKS where id = :id",
                                            Map.of("id", id),
                                            new BookMapper());

        if (!bookList.isEmpty()) {
            Author author = getAuthorById(bookList.get(0).getAuthor().getId());
            Genre genre = getGenreById(bookList.get(0).getGenre().getId());
            if ((author == null) || (genre == null)) {
                return Optional.ofNullable(null);
            }

            bookList.get(0).setAuthor(author);
            bookList.get(0).setGenre(genre);

            return Optional.of(bookList.get(0));
        } else {
            return Optional.ofNullable(null);
        }
    }

    @Override
    public List<Book> getAllBooks() {
        List<Book> bookList = jdbc.query("select id, title, author_id, genre_id from BOOKS", new BookMapper());
        if (!bookList.isEmpty()) {
            for (Book book : bookList) {
                Author author = getAuthorById(book.getAuthor().getId());
                Genre genre = getGenreById(book.getGenre().getId());

                book.setAuthor(author);
                book.setGenre(genre);
            }
        }

        return bookList;
    }

    @Override
    public void updateBook(long id, Book book) {
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("title", book.getTitle());
        params.addValue("authorId", book.getAuthor().getId());
        params.addValue("genreId", book.getGenre().getId());
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
    public List<Author> getAllAuthors() {
        return jdbc.query("select id, author from AUTHORS", new AuthorMapper());
    }

    @Override
    public List<Genre> getAllGenres() {
        return jdbc.query("select id, genre from GENRES", new GenreMapper());
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
    public Genre getGenreByName(String genreName) {
        try {
            return jdbc.queryForObject("select id, genre from GENRES where genre = :genreName",
                    Map.of("genreName", genreName),
                    new GenreMapper());
        } catch (DataAccessException e) {
            return null;
        }
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
            return new Book(id, title, new Author(authorId, ""), new Genre(genreId, ""));
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
