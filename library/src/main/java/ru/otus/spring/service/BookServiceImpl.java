package ru.otus.spring.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.spring.dao.BookDao;
import ru.otus.spring.domain.Book;
import ru.otus.spring.dto.BookDto;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {
    private final BookDao dao;

    @Override
    public BookDto createBook(Book book) {
        return new BookDto(dao.insert(book));
    }

    @Override
    public List<BookDto> getListBook() {
        return dao.getAll().stream().map(BookDto::new).collect(Collectors.toList());
    }

    @Override
    public BookDto updateBook(Book book) {
        return new BookDto(dao.update(book));
    }

    @Override
    public void deleteBook(long bookId) {
        dao.deleteById(bookId);
    }
}
