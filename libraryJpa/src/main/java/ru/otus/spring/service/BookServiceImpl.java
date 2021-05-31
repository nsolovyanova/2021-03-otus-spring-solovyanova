package ru.otus.spring.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.spring.domain.Author;
import ru.otus.spring.domain.Book;
import ru.otus.spring.domain.Genre;
import ru.otus.spring.repositories.BookRepository;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {
    private final BookRepository bookRepository;

    @Transactional(readOnly = true)
    @Override
    public Optional<Book> getById(Long id) {
        return bookRepository.getById(id);
    }

    @Transactional(readOnly = true)
    @Override
    public Optional<Book> getByName(String name) {
        return bookRepository.getByName(name);
    }

    @Override
    public List<Book> getAllBookByAuthor(Author author) {
        return null;
    }

    @Override
    public List<Book> getAllBookByGenre(Genre genre) {
        return null;
    }

    @Transactional(readOnly = true)
    @Override
    public List<Book> getAll() {
        return bookRepository.getAll();
    }

    @Override
    public Book insert(Book book) {
        return bookRepository.save(book);
    }

    @Transactional
    @Override
    public Book save(Book book) {
        return bookRepository.save(book);
    }

    @Transactional
    @Override
    public void delete(Book book) {
        bookRepository.delete(book);
    }
}
