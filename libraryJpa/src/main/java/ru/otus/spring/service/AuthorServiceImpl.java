package ru.otus.spring.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.spring.domain.Author;
import ru.otus.spring.repositories.AuthorRepository;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthorServiceImpl implements AuthorService {
    private final AuthorRepository authorRepository;

    @Transactional(readOnly = true)
    @Override
    public List<Author> getAll() {
        return authorRepository.getAll();
    }

    @Override
    @Transactional
    public Author save(Author author) {
        return authorRepository.getByName(author.getName()).stream().findFirst().orElseGet(() -> authorRepository.save(author));
    }

    @Transactional(readOnly = true)
    @Override
    public Optional<Author> getById(Long id) {
        return authorRepository.getById(id);
    }

    @Transactional(readOnly = true)
    @Override
    public Optional<Author> getByName(String name) {
        return authorRepository.getByName(name);
    }

    @Transactional
    @Override
    public void delete(Author author) {
        if (authorRepository.getByName(author.getName()).stream().findFirst().isPresent()) {
            authorRepository.delete(author);
        }
    }
}
