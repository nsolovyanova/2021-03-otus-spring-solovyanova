package ru.otus.spring.service;

import ru.otus.spring.domain.Comment;

import java.util.Optional;

public interface CommentService {
    Optional<Comment> getById(long id);

    Comment save(Comment comment);

    void delete(Comment comment);

}
