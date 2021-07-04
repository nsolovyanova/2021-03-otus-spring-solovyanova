package ru.otus.spring.service;

import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import ru.otus.spring.domain.Author;
import ru.otus.spring.domain.Book;
import ru.otus.spring.domain.Comment;
import ru.otus.spring.domain.Genre;
import ru.otus.spring.repositories.BookRepository;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@DisplayName("Класс BookServiceImpl должен")
class BookServiceImplTest {
    public static final long FIRST_BOOK_ID = 1L;
    public static final String FIRST_BOOK_NAME = "Книга1";
    public static final long SECOND_BOOK_ID = 2L;
    public static final String SECOND_BOOK_NAME = "Книга2";
    public static final long FIRST_AUTHOR_ID = 1L;
    public static final String FIRST_AUTHOR_NAME = "Куприн";
    public static final long SECOND_AUTHOR_ID = 2L;
    public static final String SECOND_AUTHOR_NAME = "Куприн2";
    public static final long FIRST_GENRE_ID = 1L;
    public static final String FIRST_GENRE_NAME = "Драмма";
    public static final long SECOND_GENRE_ID = 2L;
    public static final String SECOND_GENRE_NAME = "Драмма2";
    public static final long FIRST_COMMENT_ID = 1L;
    public static final String FIRST_COMMENT_NAME = "Комментарий1";
    public static final long SECOND_COMMENT_ID = 2L;
    public static final String SECOND_COMMENT_NAME = "Комментарий2";

    private BookService bookService;

    @Mock
    private BookRepository bookRepository;

    private Book book1;
    private Book book2;
    private Author author1;
    private Author author2;
    private Genre genre1;
    private Genre genre2;

    @BeforeAll
    public void setUp() {
        author1 = new Author(FIRST_AUTHOR_ID, FIRST_AUTHOR_NAME);
        author2 = new Author(SECOND_AUTHOR_ID, SECOND_AUTHOR_NAME);
        genre1 = new Genre(FIRST_GENRE_ID, FIRST_GENRE_NAME);
        genre2 = new Genre(SECOND_GENRE_ID, SECOND_GENRE_NAME);

        book1 = Book.builder().id(FIRST_BOOK_ID).name(FIRST_BOOK_NAME).genre(genre1).author(author1).build();
        book2 = Book.builder().id(SECOND_BOOK_ID).name(SECOND_BOOK_NAME).genre(genre2).author(author2).build();
    }

    @BeforeEach
    void init() {
        bookService = new BookServiceImpl(bookRepository);
    }

//    List<Book> getAllBookByAuthor(Author author);
//
//    List<Book> getAllBookByGenre(Genre genre);

    @Test
    @DisplayName("создавать новую книгу")
    void shouldSaveBook() {
        Comment comment = new Comment(FIRST_COMMENT_ID, book1, FIRST_COMMENT_NAME);
        List<Comment> comments = List.of(comment);
        given(bookRepository.save(any(Book.class))).willReturn(book1);
        Book actualBook = bookService.save(new Book(FIRST_BOOK_ID, FIRST_BOOK_NAME, author1, genre1, comments));
        verify(bookRepository, times(1)).save(any(Book.class));
    }

    @Test
    @DisplayName("возвращать все книги")
    void shouldGetListBook() {
        List<Book> books = List.of(book1, book2);
        List<String> expectedBooks = books.stream().map(Book::toString).collect(Collectors.toList());
        given(bookRepository.findAll()).willReturn(books);
        List<String> actualBooks = bookService.getAll().stream().map(Book::toString).collect(Collectors.toList());

        assertThat(actualBooks).hasSameElementsAs(expectedBooks);
        verify(bookRepository, times(1)).findAll();
    }

    @DisplayName("возвращать книгу по id")
    @Test
    void shouldGetById() {
        Book expectedBook = Book.builder().id(FIRST_BOOK_ID).name(FIRST_BOOK_NAME).genre(genre1).author(author1).build();
        given(bookRepository.findById(FIRST_BOOK_ID)).willReturn(Optional.of(expectedBook));
        Book book = bookService.getById(FIRST_BOOK_ID).get();

        assertThat(book.getName()).isEqualTo(FIRST_BOOK_NAME);
    }

    @DisplayName("возвращать книгу по name")
    @Test
    void shouldGetByName() {
        Book expectedBook = Book.builder().id(FIRST_BOOK_ID).name(FIRST_BOOK_NAME).genre(genre1).author(author1).build();
        given(bookRepository.findByName(FIRST_BOOK_NAME)).willReturn(Optional.of(expectedBook));
        Book book = bookService.getByName(FIRST_BOOK_NAME).get();

        assertThat(book.getId()).isEqualTo(FIRST_BOOK_ID);

    }

    @Test
    @DisplayName("удалять книгу")
    void shouldRemoveBook() {
        bookService.delete(book1);

        verify(bookRepository, times(1)).delete(book1);
    }

}
