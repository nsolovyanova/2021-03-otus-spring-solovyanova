package ru.otus.spring.rest.mapper;

import ru.otus.spring.domain.Book;
import ru.otus.spring.dto.BookDto;

public interface BookMapper {
    BookDto bookToDto(Book book);

    Book bookDtoToBook (BookDto bookDto);
}
