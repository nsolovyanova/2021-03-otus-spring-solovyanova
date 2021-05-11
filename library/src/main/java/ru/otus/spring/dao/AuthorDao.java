package ru.otus.spring.dao;


import ru.otus.spring.domain.Author;

import java.util.List;
import java.util.Optional;

public interface AuthorDao {
    Author insert(Author author);

    void update(Author author);

    Optional<Author> getByName(String name);

    void deleteById(Long id);

    Author getById(Long id);

    List<Author> getAll();

    List<Author> getListByBookId(long bookId);
}
