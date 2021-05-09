package ru.otus.spring.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.otus.spring.dao.BookDao;
import ru.otus.spring.domain.Author;
import ru.otus.spring.domain.Book;
import ru.otus.spring.domain.Genre;
import ru.otus.spring.dto.BookDto;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("Класс BookServiceImpl должен")
class BookServiceImplTest {
    public static final long FIRST_BOOK_ID = 1L;
    public static final String FIRST_BOOK_NAME = "Книга1";
    public static final long SECOND_BOOK_ID = 2L;
    public static final String SECOND_BOOK_NAME = "Книга2";
    public static final long FIRST_AUTHOR_ID = 1L;
    public static final String FIRST_AUTHOR_NAME = "Автор1";
    public static final long FIRST_GENRE_ID = 1L;
    public static final String FIRST_GENRE_NAME = "Жанр1";
    public static final long SECOND_AUTHOR_ID = 2L;
    public static final String SECOND_AUTHOR_NAME = "Автор2";
    public static final long SECOND_GENRE_ID = 2L;
    public static final String SECOND_GENRE_NAME = "Жанр2";

    private BookService bookService;

    @Mock
    private BookDao dao;

    @BeforeEach
    void setUp() {
        bookService = new BookServiceImpl(dao);
    }

    @Test
    @DisplayName("создавать новую книгу")
    void shouldCreateBook() {
        Author author = new Author(FIRST_AUTHOR_ID, FIRST_AUTHOR_NAME);
        Genre genre = new Genre(FIRST_GENRE_ID, FIRST_GENRE_NAME);
        Book book = new Book(FIRST_BOOK_ID, FIRST_BOOK_NAME, author, genre);

        BookDto expectedBook = new BookDto(book);

        doReturn(book).when(dao).insert(book);
        BookDto actualBook = bookService.createBook(book);
        verify(dao, times(1)).insert(any(Book.class));
    }

    @Test
    @DisplayName("возвращать все книги")
    void shouldGetListBook() {
        Author author = new Author(FIRST_AUTHOR_ID, FIRST_AUTHOR_NAME);
        Genre genre = new Genre(FIRST_GENRE_ID, FIRST_GENRE_NAME);
        List<Book> books = List.of(new Book(FIRST_BOOK_ID, FIRST_BOOK_NAME, author, genre),
                new Book(SECOND_BOOK_ID, SECOND_BOOK_NAME, author, genre));
        List<BookDto> expectedBooks = books.stream().map(BookDto::new).collect(Collectors.toList());
        given(dao.getAll()).willReturn(books);
        List<BookDto> actualBooks = bookService.getListBook();
        assertThat(actualBooks.size()).isEqualTo(expectedBooks.size());
    }

    @Test
    @DisplayName("изменить книгу")
    void shouldUpdateBook(){
        Author author = new Author(FIRST_AUTHOR_ID, FIRST_AUTHOR_NAME);
        Genre genre = new Genre(FIRST_GENRE_ID, FIRST_GENRE_NAME);
        Book book = new Book(FIRST_BOOK_ID, FIRST_BOOK_NAME, author, genre);
        when(dao.update(any(Book.class))).thenReturn(book);
        Author authorForUpd = new Author(SECOND_AUTHOR_ID, SECOND_AUTHOR_NAME);
        Genre genreForUpd = new Genre(SECOND_GENRE_ID, SECOND_GENRE_NAME);
        Book bookForUpd = new Book(FIRST_BOOK_ID, SECOND_BOOK_NAME, authorForUpd, genreForUpd);
        BookDto newBook = bookService.updateBook(bookForUpd);
        assertNotNull(newBook);
        assertThat(newBook.equals(bookForUpd));
    }

    @Test
    @DisplayName("удалить книгу")
    void shouldDeleteBook(){
        bookService.deleteBook(FIRST_BOOK_ID);
        verify(dao, times(1)).deleteById(FIRST_BOOK_ID);
    }

}
