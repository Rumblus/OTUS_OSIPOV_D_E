package ru.otus.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.dao.AuthorDao;
import ru.otus.domain.Author;

import java.util.List;

@AllArgsConstructor
@Service
public class AuthorManagerImpl implements AuthorManager {

    private final AuthorDao authorDao;

    @Override
    public List<Author> getAllAuthors() {
        return authorDao.findAll();
    }
}
