package ru.otus.mongock.changelog;

import com.github.cloudyrock.mongock.ChangeLog;
import com.github.cloudyrock.mongock.ChangeSet;
import com.mongodb.client.MongoDatabase;
import ru.otus.dao.AuthorDao;
import ru.otus.dao.BookDao;
import ru.otus.dao.GenreDao;
import ru.otus.domain.Author;
import ru.otus.domain.Book;
import ru.otus.domain.Comment;
import ru.otus.domain.Genre;

import java.util.List;

@ChangeLog
public class DatabaseChangelog {

    @ChangeSet(order = "001", id = "dropDb", author = "me", runAlways = true)
    public void dropDb(MongoDatabase db) {
        db.drop();
    }

    @ChangeSet(order = "002", id = "insertAuthors", author = "me")
    public void insertAuthors(AuthorDao repository) {
        repository.saveAll(List.of(
                new Author("Robert Lewis Stevenson"),
                new Author("Lev Tolstoi"),
                new Author("Alexander Pushkin")
        ));
    }

    @ChangeSet(order = "003", id = "insertGenres", author = "me")
    public void insertGenres(GenreDao repository) {
        repository.saveAll(List.of(
                new Genre("Novel"),
                new Genre("Fairy Tale"),
                new Genre("Detective"),
                new Genre("Adventure"),
                new Genre("Horror")
        ));
    }

    @ChangeSet(order = "004", id = "insertBooks", author = "me")
    public void insertBooks(BookDao repository, AuthorDao authorDao, GenreDao genreDao) {
        repository.save(
                new Book("Black Arrow",
                        authorDao.findByName("Robert Lewis Stevenson").get(),
                        genreDao.findByName("Novel").get(),
                        List.of(new Comment("Very interesting book!")))
        );
    }
}
