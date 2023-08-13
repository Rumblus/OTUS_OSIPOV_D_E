package ru.otus.dao;

import lombok.RequiredArgsConstructor;
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
public class BookDaoJdbc implements BookDao {
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
        List<Book> bookList = jdbc.query("select b.id as bookId, b.title, b.author_id, b.genre_id, a.author, g.genre " +
                        "from BOOKS b join AUTHORS a on b.author_id = a.id " +
                        "join GENRES g on b.genre_id = g.id " +
                        "where b.id = :id",
                Map.of("id", id),
                new BookMapper());

        if (!bookList.isEmpty()) {
            return Optional.of(bookList.get(0));
        } else {
            return Optional.ofNullable(null);
        }
    }

    @Override
    public List<Book> getAllBooks() {
        List<Book> bookList = jdbc.query("select b.id as bookId, b.title, b.author_id, b.genre_id, a.author, g.genre " +
                        "from BOOKS b join AUTHORS a on b.author_id = a.id " +
                        "join GENRES g on b.genre_id = g.id",
                new BookMapper());
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

    private static class BookMapper implements RowMapper<Book> {
        @Override
        public Book mapRow(ResultSet resultSet, int i) throws SQLException {
            if (resultSet == null) {
                return null;
            }

            long id = resultSet.getLong("bookId");
            String title = resultSet.getString("title");
            long authorId = resultSet.getLong("author_id");
            String authorName = resultSet.getString("author");
            long genreId = resultSet.getLong("genre_id");
            String genreName = resultSet.getString("genre");
            return new Book(id, title, new Author(authorId, authorName), new Genre(genreId, genreName));
        }
    }
}
