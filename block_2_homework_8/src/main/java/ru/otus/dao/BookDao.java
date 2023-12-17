package ru.otus.dao;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.query.Param;
import ru.otus.domain.Book;

import java.util.List;
import java.util.Optional;

public interface BookDao extends MongoRepository<Book, String> {

    Optional<Book> findByTitle(@Param("title") String title);

    List<Book> findAll();
}
