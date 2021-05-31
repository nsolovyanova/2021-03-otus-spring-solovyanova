package ru.otus.spring.repositories;

import ru.otus.spring.domain.Author;
import ru.otus.spring.domain.Book;
import ru.otus.spring.domain.Genre;

import java.util.List;
import java.util.Optional;

public interface BookRepository {
    Optional<Book> getById(Long id);

    Optional<Book> getByName(String name);

    List<Book> getAllBookByAuthor(Author author);

    List<Book> getAllBookByGenre(Genre genre);

    List<Book> getAll();

    Book save(Book book);

    void delete(Book book);

}
