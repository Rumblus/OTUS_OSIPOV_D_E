package ru.otus.dao;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.query.Param;
import ru.otus.domain.Genre;

import java.util.List;
import java.util.Optional;

public interface GenreDao extends MongoRepository<Genre, String> {

    Optional<Genre> findByName(@Param("name") String name);

    List<Genre> findAll();
}
