package ru.otus.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.otus.domain.security.Role;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Role findByName(String name);
}
