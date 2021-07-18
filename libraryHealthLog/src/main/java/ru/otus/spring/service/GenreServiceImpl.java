package ru.otus.spring.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.spring.domain.Genre;
import ru.otus.spring.repositories.GenreRepository;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class GenreServiceImpl implements GenreService {
    private final GenreRepository genreRepository;

    @Transactional(readOnly = true)
    @Override
    public List<Genre> getAll() {
        return genreRepository.findAll();
    }

    @Transactional
    @Override
    public Genre save(Genre genre) {
        return genreRepository.findByName(genre.getName()).stream().findFirst().orElseGet(() -> genreRepository.save(genre));
    }

    @Transactional(readOnly = true)
    @Override
    public Optional<Genre> getById(long id) {
        return genreRepository.findById(id);
    }

    @Transactional(readOnly = true)
    @Override
    public Optional<Genre> getByName(String name) {
        return genreRepository.findByName(name).stream().findFirst();
    }

    @Transactional
    @Override
    public void delete(Genre genre) {
        if (genreRepository.findByName(genre.getName()).stream().findFirst().isPresent()) {
            genreRepository.delete(genre);
        }
    }
}
