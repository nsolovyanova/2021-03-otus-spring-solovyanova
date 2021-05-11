package ru.otus.spring.dao;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;
import org.springframework.dao.EmptyResultDataAccessException;
import ru.otus.spring.domain.Genre;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatCode;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

@DisplayName("Класс GenreDaoJdbc должен")
@JdbcTest
@Import(GenreDaoJdbc.class)
class GenreDaoJdbcTest {

    @Autowired
    private GenreDao genreDao;

    @Test
    @DisplayName("должен добавлять жанр")
    void shouldInsertGenre() {
        Genre newGenre = new Genre("Рассказ");
        Genre insGenre = genreDao.insert(newGenre);
        assertThat(insGenre.getId()).isPositive();
        assertThat(insGenre.getName()).isEqualTo(newGenre.getName());
    }

    @Test
    @DisplayName("должен искать жанр по id")
    void shouldFindGenreById() {
        Genre expectedGenre = new Genre("Рассказ");
        genreDao.insert(expectedGenre);
        Genre actualGenre = genreDao.getById(expectedGenre.getId());
        assertThat(actualGenre).usingRecursiveComparison().isEqualTo(expectedGenre);
    }

    @Test
    @DisplayName("должен удалять жанр")
    void shouldDeleteGenre() {
        assertThatCode(() -> genreDao.getById(1L))
                .doesNotThrowAnyException();

        genreDao.deleteById(1L);

        assertThatThrownBy(() -> genreDao.getById(1L))
                .isInstanceOf(EmptyResultDataAccessException.class);
    }

    @Test
    @DisplayName("должен возвращать все жанры")
    void shouldFindAllGenres() {
        List<Genre> listExpected = new ArrayList<>();
        listExpected.add(new Genre(1L, "Детская литература"));
        listExpected.add(new Genre(2L, "Рассказ"));
        listExpected.add(new Genre(3L, "НЕ Детская литература"));
        List<Genre> listActual = genreDao.getAll();
        assertThat(listActual.size()).isEqualTo(listExpected.size());
        assertThat(listActual).hasSameElementsAs(listExpected);
    }

    @Test
    @DisplayName("должен находить жанр по названию")
    void shouldUpdateGenre() {
        Genre genreExpected = new Genre(3L, "Комедия");
        genreDao.insert(genreExpected);
        Optional<Genre> genreActual = genreDao.getByName("Комедия");
        assertThat(genreActual.isPresent()).isTrue();
        assertThat(genreActual.get()).isEqualTo(genreExpected);
    }

}
