package ru.otus.spring.rest.mapper;

import ru.otus.spring.domain.Author;
import ru.otus.spring.dto.AuthorDto;

public interface AuthorMapper {
    AuthorDto authorToDto(Author author);

    Author authorDtoToAuthor (AuthorDto authorDto);
}
