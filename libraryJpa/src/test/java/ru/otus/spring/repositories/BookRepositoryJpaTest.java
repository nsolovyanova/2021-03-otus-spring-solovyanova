package ru.otus.spring.repositories;

import lombok.val;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;
import ru.otus.spring.domain.Author;
import ru.otus.spring.domain.Book;
import ru.otus.spring.domain.Comment;
import ru.otus.spring.domain.Genre;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Тест репозитория на основе Jpa для работы со книгами BookRepositoryJpa")
@DataJpaTest
@Import(BookRepositoryJpa.class)
public class BookRepositoryJpaTest {
    private static final int EXPECTED_NUMBER_OF_BOOKS = 2;
    private static final long FIRST_BOOK_ID = 1L;
    private static final String FIRST_BOOK_NAME = "Стихи";
    private static final String TEST_COMMENT_NAME = "Самая лучшая книга";
    private static final String TEST_AUTHER_NAME = "Стивен Кинг";
    private static final String TEST_GENRE_NAME = "Фантастика";
    private static final String TEST_BOOK_NAME = "Оно";

    private static final String BOOK_BY_AUTHOR_NAME = "Крапивин Владислав";
    private static final long BOOK_BY_AUTHOR_ID = 2L;
    private static final int EXPECTED_NUMBER_OF_BOOKS_BY_AUTHOR = 1;
    private static final String BOOK_BY_GENRE_NAME = "Рассказ";
    private static final long BOOK_BY_GENRE_ID = 2L;
    private static final int EXPECTED_NUMBER_OF_BOOKS_BY_GENRE = 1;

    @Autowired
    private BookRepositoryJpa repositoryJpa;

    @Autowired
    private TestEntityManager em;

    @DisplayName("Возвращать книгу по id")
    @Test
    void shouldReturnBookById() {
        val actualBook = repositoryJpa.getById(FIRST_BOOK_ID);
        val expectedBook = em.find(Book.class, FIRST_BOOK_ID);
        assertThat(actualBook).isPresent().get()
                .usingRecursiveComparison().isEqualTo(expectedBook);
    }

    @DisplayName("Возвращать книгу по name")
    @Test
    void shouldReturnBookByName() {
        val actualBook = repositoryJpa.getByName(FIRST_BOOK_NAME);
        val expectedBook = em.find(Book.class, FIRST_BOOK_ID);
        assertThat(actualBook).isPresent().get()
                .usingRecursiveComparison().isEqualTo(expectedBook);
    }

    @DisplayName("Возвращать книги по автору")
    @Test
    void shouldReturnAllBooksByAuthor() {
        val author = new Author(BOOK_BY_AUTHOR_ID, BOOK_BY_AUTHOR_NAME, new ArrayList<>());

        val books = repositoryJpa.getAllBookByAuthor(author);
        assertThat(books).isNotNull().hasSize(EXPECTED_NUMBER_OF_BOOKS_BY_AUTHOR)
                .allMatch(book -> book.getGenre() != null)
                .allMatch(book -> book.getAuthor() != null && book.getAuthor().getName().equals(BOOK_BY_AUTHOR_NAME));
    }

    @DisplayName("Возвращать книги по жанру")
    @Test
    void shouldReturnAllBooksByGenre() {
        val genre = new Genre(BOOK_BY_GENRE_ID, BOOK_BY_GENRE_NAME);

        val books = repositoryJpa.getAllBookByGenre(genre);
        assertThat(books).isNotNull().hasSize(EXPECTED_NUMBER_OF_BOOKS_BY_GENRE)
                .allMatch(book -> book.getAuthor() != null)
                .allMatch(book -> book.getGenre() != null && book.getGenre().getName().equals(BOOK_BY_GENRE_NAME));
    }

    @DisplayName("Возвращать все книги")
    @Test
    void shouldReturnAllBooks() {
        val genres = repositoryJpa.getAll();
        assertThat(genres).isNotNull().hasSize(EXPECTED_NUMBER_OF_BOOKS);
    }

    @DisplayName("Сохранять всю информацию о книге")
    @Test
    void shouldSaveBook() {
        val author = new Author(0L, TEST_AUTHER_NAME, new ArrayList<>());
        val genre = new Genre(0L, TEST_GENRE_NAME);
        val book = Book.builder().id(0).name(TEST_BOOK_NAME).author(author).genre(genre).build();

        repositoryJpa.save(book);
        assertThat(book.getId()).isGreaterThan(0);

        val actualBook = em.find(Book.class, book.getId());
        assertThat(actualBook).isNotNull().matches(b -> b.getName().equals(TEST_BOOK_NAME))
                .matches(b -> b.getAuthor() != null && b.getAuthor().getId() > 0 && b.getAuthor().getName().equals(TEST_AUTHER_NAME))
                .matches(b -> b.getGenre() != null && b.getGenre().getId() > 0 && b.getGenre().getName().equals(TEST_GENRE_NAME));
    }

    @DisplayName("Удалять книгу")
    @Test
    void shouldDeleteBook() {
        val actualBook = em.find(Book.class, FIRST_BOOK_ID);
        assertThat(actualBook).isNotNull();

        repositoryJpa.delete(actualBook);
        val deletedBook = em.find(Book.class, FIRST_BOOK_ID);

        assertThat(deletedBook).isNull();
    }

}
