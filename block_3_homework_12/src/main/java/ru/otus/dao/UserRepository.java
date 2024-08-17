package ru.otus.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.otus.domain.security.User;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);
}
