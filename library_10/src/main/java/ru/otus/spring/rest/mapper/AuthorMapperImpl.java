package ru.otus.spring.rest.mapper;

import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import ru.otus.spring.domain.Author;
import ru.otus.spring.dto.AuthorDto;

@Service
@AllArgsConstructor
public class AuthorMapperImpl implements AuthorMapper {
    private final ModelMapper modelMapper;

    @Override
    public AuthorDto authorToDto(Author author) {
        return modelMapper.map(author, AuthorDto.class);
    }

    @Override
    public Author authorDtoToAuthor(AuthorDto authorDto) {
        return modelMapper.map(authorDto, Author.class);
    }
}
