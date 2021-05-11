package ru.otus.spring.dao;


import ru.otus.spring.domain.Genre;

import java.util.List;
import java.util.Optional;

public interface GenreDao {
    Genre insert(Genre genre);

    void update(Genre genre);

    Optional<Genre> getByName(String name);

    void deleteById(Long id);

    Genre getById(Long id);

    List<Genre> getAll();
}
