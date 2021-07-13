package ru.otus.spring.rest.mapper;

import ru.otus.spring.domain.Genre;
import ru.otus.spring.dto.GenreDto;

public interface GenreMapper {
    GenreDto genreToDto(Genre genre);

    Genre genreDtoToGenre (GenreDto genreDto);
}
