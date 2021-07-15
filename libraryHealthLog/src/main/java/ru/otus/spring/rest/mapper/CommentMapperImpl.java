package ru.otus.spring.rest.mapper;

import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import ru.otus.spring.domain.Book;
import ru.otus.spring.domain.Comment;
import ru.otus.spring.dto.CommentDto;

@Service
@AllArgsConstructor
public class CommentMapperImpl implements CommentMapper {
    private final ModelMapper modelMapper;

    @Override
    public CommentDto commentToDto(Comment comment) {
        return modelMapper.map(comment, CommentDto.class);
    }

    @Override
    public Comment commentDtoToComment(CommentDto commentDto, Book book) {
        Comment comment = modelMapper.map(commentDto, Comment.class);
        comment.setBook(book);
        return comment;
    }
}
