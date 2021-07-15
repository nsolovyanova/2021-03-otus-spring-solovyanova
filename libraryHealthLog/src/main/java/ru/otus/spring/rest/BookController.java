package ru.otus.spring.rest;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import ru.otus.spring.domain.Book;
import ru.otus.spring.dto.BookDto;
import ru.otus.spring.dto.CommentDto;
import ru.otus.spring.exception.BookServiceBookNotFoundException;
import ru.otus.spring.rest.mapper.BookMapper;
import ru.otus.spring.rest.mapper.CommentMapper;
import ru.otus.spring.service.BookService;
import ru.otus.spring.service.CommentService;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@AllArgsConstructor
@RequestMapping("/spring")
public class BookController {
    private final BookService bookService;
    private final CommentService commentService;
    private final BookMapper bookMapper;
    private final CommentMapper commentMapper;

    @GetMapping("/books")
    public List<BookDto> getBook() {
        return bookService.getAll().stream()
                .map(bookMapper::bookToDto)
                .collect(Collectors.toList());
    }

    @GetMapping("/books/{id}")
    public BookDto getBookById(@PathVariable("id") long id) {
        Book book = bookService.getById(id).orElseThrow(() -> new BookServiceBookNotFoundException(id));
        return bookMapper.bookToDto(book);
    }

    @PostMapping("/books")
    public BookDto saveBook(@RequestBody BookDto bookDto) {
        Book book = bookService.insert(bookMapper.bookDtoToBook(bookDto));
        return bookDto;
    }

    @PutMapping("/books")
    public BookDto updateBook(@RequestBody BookDto bookDto) {
        Book book = bookService.save(bookMapper.bookDtoToBook(bookDto));
        return bookDto;
    }

    @DeleteMapping("/books/{id}")
    @Transactional
    public void deleteBook(@PathVariable("id") long id) {
        bookService.delete(bookService.getById(id).orElse(null));
    }

    @GetMapping("/books/{id}/comments")
    public List<CommentDto> getCommentsByBook(@PathVariable("id") long id) {
        Book book = bookService.getById(id).orElse(null);
        List<CommentDto> commentDtoList = commentService.getAllCommentsByBook(book).stream()
                .map(commentMapper::commentToDto)
                .collect(Collectors.toList());
        return commentDtoList;
    }

    @ExceptionHandler(BookServiceBookNotFoundException.class)
    public ResponseEntity<String> handleBookNotFound(BookServiceBookNotFoundException ex) {
        return ResponseEntity.badRequest().body(ex.getMessage());
    }

}
