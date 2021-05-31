package ru.otus.spring.repositories;

import ru.otus.spring.domain.Genre;

import java.util.List;
import java.util.Optional;

public interface GenreRepository {
    List<Genre> getAll();

    Genre save(Genre genre);

    Optional<Genre> getById(Long id);

    Optional<Genre> getByName(String name);

    void delete(Genre genre);
}
