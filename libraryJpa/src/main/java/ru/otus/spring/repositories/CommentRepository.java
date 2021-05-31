package ru.otus.spring.repositories;

import java.util.List;
import java.util.Optional;

import ru.otus.spring.domain.Book;
import ru.otus.spring.domain.Comment;

public interface CommentRepository {
    Optional<Comment> getById(long id);

    List<Comment> getAllCommentsByBook(Book book);

    Comment save (Comment comment);

    void delete (Comment comment);
}
