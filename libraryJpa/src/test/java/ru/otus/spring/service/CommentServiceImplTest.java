package ru.otus.spring.service;

import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.otus.spring.domain.Author;
import ru.otus.spring.domain.Book;
import ru.otus.spring.domain.Comment;
import ru.otus.spring.domain.Genre;
import ru.otus.spring.repositories.CommentRepository;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@DisplayName("Класс CommentServiceImpl должен")
public class CommentServiceImplTest {
    public static final long FIRST_BOOK_ID = 1L;
    public static final String FIRST_BOOK_NAME = "Книга1";
    public static final long FIRST_AUTHOR_ID = 1L;
    public static final String FIRST_AUTHOR_NAME = "Куприн";
    public static final long FIRST_GENRE_ID = 1L;
    public static final String FIRST_GENRE_NAME = "Драмма";
    public static final long FIRST_COMMENT_ID = 1L;
    public static final String FIRST_COMMENT_NAME = "Комментарий1";

    private CommentService сommentService;

    @Mock
    private CommentRepository сommentRepository;

    private Book book;
    private Author author;
    private Genre genre;
    private Comment comment;

    @BeforeAll
    public void setUp() {
        author = new Author(FIRST_AUTHOR_ID, FIRST_AUTHOR_NAME);
        genre = new Genre(FIRST_GENRE_ID, FIRST_GENRE_NAME);
        book = Book.builder().id(FIRST_BOOK_ID).name(FIRST_BOOK_NAME).genre(genre).author(author).build();
        comment = new Comment(FIRST_COMMENT_ID, book, FIRST_COMMENT_NAME);
    }

    @BeforeEach
    void init() {
        сommentService = new CommentServiceImpl(сommentRepository);
    }

    @Test
    @DisplayName("создавать новый комментарий")
    void shouldSaveGenre() {
        given(сommentRepository.save(any(Comment.class))).willReturn(comment);
        Comment actualGenre = сommentService.save(new Comment(FIRST_COMMENT_ID, book, FIRST_COMMENT_NAME));
        verify(сommentRepository, times(1)).save(any(Comment.class));
    }

    @DisplayName("возвращать комментарий по id")
    @Test
    void shouldGetById() {
        given(сommentRepository.getById(FIRST_COMMENT_ID)).willReturn(Optional.of(comment));
        Comment exComment = сommentRepository.getById(FIRST_COMMENT_ID).get();

        assertThat(exComment.getText_comment()).isEqualTo(FIRST_COMMENT_NAME);
    }


}
