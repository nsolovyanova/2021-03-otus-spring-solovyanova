package ru.otus.spring.rest.mapper;

import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import ru.otus.spring.domain.Book;
import ru.otus.spring.dto.BookDto;

@Service
@AllArgsConstructor
public class BookMapperImpl implements BookMapper {
    private final ModelMapper modelMapper;
    private final AuthorMapper authorMapper;
    private final GenreMapper genreMapper;
    private final CommentMapper commentMapper;


    @Override
    public BookDto bookToDto(Book book) {
        BookDto bookDto = modelMapper.map(book, BookDto.class);

        bookDto.setAuthorDto(authorMapper.authorToDto(book.getAuthor()));
        bookDto.setGenreDto(genreMapper.genreToDto(book.getGenre()));
//        bookDto.setGenreDto(commentMapper.commentToDto(book.getComment()).orElse(null));

        return bookDto;
    }

    @Override
    public Book bookDtoToBook(BookDto bookDto) {
        Book book = modelMapper.map(bookDto, Book.class);

        book.setAuthor(authorMapper.authorDtoToAuthor(bookDto.getAuthorDto()));
        book.setGenre(genreMapper.genreDtoToGenre(bookDto.getGenreDto()));
        //book.setComment(commentMapper.commentDtoToComment(bookDto.getCommentDtoList(), book).orElse(null));

        return book;
    }

}
