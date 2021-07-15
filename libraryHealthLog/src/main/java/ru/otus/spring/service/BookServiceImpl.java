package ru.otus.spring.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.spring.domain.Author;
import ru.otus.spring.domain.Book;
import ru.otus.spring.domain.Genre;
import ru.otus.spring.exception.BookServiceBookNotFoundException;
import ru.otus.spring.repositories.BookRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {
    private final BookRepository bookRepository;

    @Transactional(readOnly = true)
    @Override
    public Optional<Book> getById(long id) {
        return bookRepository.findById(id);
    }

    @Transactional(readOnly = true)
    @Override
    public Optional<Book> getByName(String name) {
        return bookRepository.findByName(name).stream().findFirst();
    }

    @Override
    @Transactional(readOnly = true)
    public List<Book> getAllBookByAuthor(Author author) {
        if (author != null) {
            return bookRepository.getAllBookByAuthor(author);
        }
        return new ArrayList<>();
    }

    @Override
    @Transactional(readOnly = true)
    public List<Book> getAllBookByGenre(Genre genre) {
        if (genre != null) {
            return bookRepository.getAllBookByGenre(genre);
        }
        return new ArrayList<>();
    }

    @Transactional(readOnly = true)
    @Override
    public List<Book> getAll() {
        return bookRepository.findAll();
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
        if (bookRepository.existsById(book.getId()))
            bookRepository.delete(book);
        else
            throw new BookServiceBookNotFoundException(book.getId());
    }
}
