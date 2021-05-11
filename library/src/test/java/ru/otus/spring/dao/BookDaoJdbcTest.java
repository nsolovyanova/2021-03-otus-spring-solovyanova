package ru.otus.spring.dao;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;
import ru.otus.spring.domain.Author;
import ru.otus.spring.domain.Book;
import ru.otus.spring.domain.Genre;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Класс BookDaoJdbc должен")
@JdbcTest
@Import(BookDaoJdbc.class)
class BookDaoJdbcTest {
    public static final long INSERTED_BOOK_ID = 2L;
    public static final String INSERTED_BOOK_NAME = "Мальчик со шпагой";
    public static final long DEFAULT_BOOK_ID = 1L;
    public static final String DEFAULT_BOOK_NAME = "Стихи";
    public static final String DEFAULT_AUTHOR_NAME = "Агния Барто";
    public static final long DEFAULT_AUTHOR_ID = 1L;
    public static final String DEFAULT_GENRE_NAME = "Детская литература";
    public static final long DEFAULT_GENRE_ID = 1L;

    @Autowired
    private BookDao bookDao;

    @Test
    @DisplayName("должен добавлять книгу")
    void shouldInsertBook() {
        Book newBook = new Book(DEFAULT_BOOK_NAME, new Author(DEFAULT_AUTHOR_NAME), new Genre(DEFAULT_GENRE_NAME));
        Book insBook = bookDao.insert(newBook);
        assertThat(insBook.getId()).isGreaterThan(0L);
        assertThat(insBook.getName()).isEqualTo(newBook.getName());
    }

    @Test
    @DisplayName("должен искать книгу по id")
    void shouldFindBookById() {
        Author expectedAuthor = new Author(1L, "Агния Барто");
        Genre expectedGenre = new Genre(1L, "Детская литература");
        Book findBook = new Book("Стихи", expectedAuthor, expectedGenre);
        Book expectedBook = new Book(1L, findBook.getName(), findBook.getAuthor(), findBook.getGenre());

        Book actualBook = bookDao.getById(expectedBook.getId()).orElse(new Book("Стихи", expectedAuthor, expectedGenre));
        assertThat(actualBook).usingRecursiveComparison().isEqualTo(expectedBook);
    }

    @Test
    @DisplayName("должен удалять книгу")
    void shouldDeleteBook() {
        Book fndBook = bookDao.getById(1L).orElse(null);
        assertThat(fndBook.getId()).isGreaterThan(0);

        bookDao.deleteById(fndBook.getId());

        Book actBook = bookDao.getById(1L).orElse(null);
        assertThat(actBook).usingRecursiveComparison().isEqualTo(null);
    }

    @Test
    @DisplayName("должен возвращать все книги")
    void shouldFindAllBooks() {
        List<Book> listExpected = new ArrayList<>();
        listExpected.add(new Book(1L, "Стихи", new Author(1L, "Агния Барто"), new Genre(1L, "Детская литература")));
        listExpected.add(new Book(2L, "Мальчик со шпагой", new Author(2L, "Крапивин Владислав"), new Genre(2L, "Рассказ")));
        List<Book> listActual = bookDao.getAll();
        assertThat(listActual.size()).isEqualTo(listExpected.size());
        assertThat(listActual).hasSameElementsAs(listExpected);
    }

    @Test
    @DisplayName("должен находить книгу по названию")
    void shouldFindBookByName() {
        Author expectedAuthor = new Author(1L, "Агния Барто");
        Genre expectedGenre = new Genre(1L, "Детская литература");
        Book findBook = new Book("Стихи", expectedAuthor, expectedGenre);
        Book expectedBook = new Book(1L, findBook.getName(), findBook.getAuthor(), findBook.getGenre());

        Book actualBook = bookDao.getByName(expectedBook.getName()).orElse(new Book("Стихи", expectedAuthor, expectedGenre));
        assertThat(actualBook).usingRecursiveComparison().isEqualTo(expectedBook);
    }

    @Test
    @DisplayName("должен изменять книгу")
    void shouldUpdateBook() {
        Author newAuthor = new Author(3L, "Агния Барто2");
        Genre newGenre = new Genre(3L, "НЕ Детская литература");
        Book beforeBook = bookDao.getById(DEFAULT_BOOK_ID).orElse(null);
        assertThat(beforeBook.getId()).isGreaterThan(0L);
        assertThat(beforeBook.getAuthor()).isNotEqualTo(newAuthor);
        assertThat(beforeBook.getGenre()).isNotEqualTo(newGenre);
        Book newBook = new Book(beforeBook.getId(), "Новые стихи", newAuthor, newGenre);

        bookDao.update(newBook);

        Book actualBook = bookDao.getById(newBook.getId()).orElse(null);
        assertThat(actualBook).usingRecursiveComparison().isEqualTo(newBook);
    }

}
