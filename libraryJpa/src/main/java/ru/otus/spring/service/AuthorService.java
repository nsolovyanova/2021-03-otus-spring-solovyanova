package ru.otus.spring.service;

import ru.otus.spring.domain.Author;

import java.util.List;
import java.util.Optional;

public interface AuthorService {
    List<Author> getAll();

    Author save(Author author);

    Optional<Author> getById(Long id);

    Optional<Author> getByName(String name);

    void delete(Author author);
}
