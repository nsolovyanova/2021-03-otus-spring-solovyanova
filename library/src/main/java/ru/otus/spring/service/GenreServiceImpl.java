package ru.otus.spring.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.spring.dao.GenreDao;
import ru.otus.spring.domain.Genre;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class GenreServiceImpl implements GenreService {
    private final GenreDao dao;

    @Override
    public Optional<Genre> createGenre(Genre genre) {
        dao.insert(genre);
        return dao.getByName(genre.getName());
    }

    @Override
    public List<String> getListGenre() {
        List<Genre> genres = dao.getAll();
        return genres.stream().map(Genre::toString).collect(Collectors.toList());
    }

}
