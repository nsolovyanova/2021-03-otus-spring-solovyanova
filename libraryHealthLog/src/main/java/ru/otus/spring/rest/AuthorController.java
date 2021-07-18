package ru.otus.spring.rest;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.otus.spring.dto.AuthorDto;
import ru.otus.spring.rest.mapper.AuthorMapper;
import ru.otus.spring.service.AuthorService;

import java.util.List;
import java.util.stream.Collectors;


@RestController
@AllArgsConstructor
@RequestMapping("/spring")
public class AuthorController {
    private final AuthorService authorService;
    private final AuthorMapper authorMapper;

    @GetMapping("/authors")
    public List<AuthorDto> getAuthor() {
        return authorService.getAll().stream()
                .map(authorMapper::authorToDto)
                .collect(Collectors.toList());
    }

}
