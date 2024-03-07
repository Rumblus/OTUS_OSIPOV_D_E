package ru.otus.dao;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.query.Param;
import ru.otus.domain.Author;

import java.util.List;
import java.util.Optional;

public interface AuthorDao extends MongoRepository<Author, String> {

    Optional<Author> findByName(@Param("name") String name);

    List<Author> findAll();
}
