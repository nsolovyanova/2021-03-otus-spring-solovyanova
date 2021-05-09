package ru.otus.spring.service;

import ru.otus.spring.domain.Author;

import java.util.List;
import java.util.Optional;

public interface AuthorService {
    Optional<Author> createAuthor(Author author);

    List<String> getListAuthor();
}
