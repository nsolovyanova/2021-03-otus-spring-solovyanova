package ru.otus.spring.service;

import ru.otus.spring.domain.Author;
import ru.otus.spring.domain.Book;
import ru.otus.spring.domain.Genre;

import java.util.List;
import java.util.Optional;

public interface BookService {
    Optional<Book> getById(long id);

    Optional<Book> getByName(String name);

    List<Book> getAllBookByAuthor(Author author);

    List<Book> getAllBookByGenre(Genre genre);

    List<Book> getAll();

    Book insert(Book book);

    Book save(Book book);

    void delete(Book book);
}
