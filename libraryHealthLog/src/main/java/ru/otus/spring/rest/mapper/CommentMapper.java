package ru.otus.spring.rest.mapper;

import ru.otus.spring.domain.Book;
import ru.otus.spring.domain.Comment;
import ru.otus.spring.dto.CommentDto;

public interface CommentMapper {
    CommentDto commentToDto(Comment comment);

    Comment commentDtoToComment (CommentDto commentDto, Book book);
}
