package ru.otus.spring.rest;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.otus.spring.dto.GenreDto;
import ru.otus.spring.rest.mapper.GenreMapper;
import ru.otus.spring.service.GenreService;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@AllArgsConstructor
@RequestMapping("/spring")
public class GenreController {
    private final GenreService genreService;
    private final GenreMapper genreMapper;

    @GetMapping("/genres")
    public List<GenreDto> getGenre() {
        return genreService.getAll().stream()
                .map(genreMapper::genreToDto)
                .collect(Collectors.toList());
    }

}
