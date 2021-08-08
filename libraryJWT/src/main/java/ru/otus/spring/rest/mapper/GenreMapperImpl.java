package ru.otus.spring.rest.mapper;

import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import ru.otus.spring.domain.Genre;
import ru.otus.spring.dto.GenreDto;

@Service
@AllArgsConstructor
public class GenreMapperImpl implements GenreMapper {
    private final ModelMapper modelMapper;

    @Override
    public GenreDto genreToDto(Genre genre) {
        return modelMapper.map(genre, GenreDto.class);
    }

    @Override
    public Genre genreDtoToGenre(GenreDto genreDto) {
        return modelMapper.map(genreDto, Genre.class);
    }
}
