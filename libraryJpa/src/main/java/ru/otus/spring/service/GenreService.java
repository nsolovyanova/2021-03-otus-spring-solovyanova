package ru.otus.spring.service;

import ru.otus.spring.domain.Genre;

import java.util.List;
import java.util.Optional;

public interface GenreService {
    List<Genre> getAll();

    Genre save(Genre genre);

    Optional<Genre> getById(long id);

    Optional<Genre> getByName(String name);

    void delete(Genre genre);
}
