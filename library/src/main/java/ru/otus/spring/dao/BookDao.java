package ru.otus.spring.dao;

import ru.otus.spring.domain.Book;
import ru.otus.spring.domain.Genre;

import java.util.List;
import java.util.Optional;

public interface BookDao {
    Book insert(Book book);
    Book update(Book book);
    Optional<Book> getByName(String name);
    void deleteById(Long id);
    Optional<Book> getById(Long id);
    List<Book> getAll();
//    List<Book> getAllByAuthorId(long authorId);
//    List<Book> getAllByGenreId(long genreId);
}
