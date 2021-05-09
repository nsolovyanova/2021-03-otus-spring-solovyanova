package ru.otus.spring.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.spring.dao.AuthorDao;
import ru.otus.spring.domain.Author;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AuthorServiceImpl implements AuthorService{
    private final AuthorDao dao;

    @Override
    public Optional<Author> createAuthor(Author author) {
        dao.insert(author);
        return dao.getByName(author.getName());
    }

    @Override
    public List<String> getListAuthor() {
        List<Author> authors = dao.getAll();
        return authors.stream().map(Author::toString).collect(Collectors.toList());
    }
}
