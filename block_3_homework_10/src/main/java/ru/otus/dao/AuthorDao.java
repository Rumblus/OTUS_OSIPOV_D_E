package ru.otus.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import ru.otus.domain.Author;
import java.util.Optional;

public interface AuthorDao extends JpaRepository<Author, Long> {

    Optional<Author> findByName(@Param("name") String name);
}
