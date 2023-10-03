package ru.otus.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.otus.domain.Genre;
import java.util.Optional;

public interface GenreDao extends JpaRepository<Genre, Long> {

    @Query("select g from Genre g where g.name = :name")
    Optional<Genre> findByName(@Param("name") String name);
}
