package ru.otus.spring.service;

import ru.otus.spring.domain.Book;
import ru.otus.spring.dto.BookDto;

import java.util.List;

public interface BookService {
    BookDto createBook(Book book);

    List<BookDto> getListBook();

    BookDto updateBook(Book book);

    void deleteBook(long bookId);
}
