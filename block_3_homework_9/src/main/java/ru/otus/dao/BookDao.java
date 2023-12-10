package ru.otus.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.otus.domain.Book;
import java.util.Optional;

public interface BookDao extends JpaRepository<Book, Long> {

    @Query("select b from Book b where b.title = :title")
    Optional<Book> findByTitle(@Param("title") String title);
}
