package ru.otus.spring.repositories;

import lombok.val;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;
import ru.otus.spring.domain.Genre;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Тест репозитория на основе Jpa для работы с жанрами GenreRepositoryJpa")
@DataJpaTest
@Import(GenreRepositoryJpa.class)
public class GenreRepositoryJpaTest {
    private static final String TEST_GENRE_NAME = "Фантастика";
    private static final String FIRST_GENRE_NAME = "Детская литература";
    private static final long FIRST_GENRE_ID = 1L;
    private static final long TEST_GENRE_ID = 1L;
    private static final int EXPECTED_NUMBER_OF_GENRES = 3;

    @Autowired
    private GenreRepositoryJpa genreRepositoryJpa;

    @Autowired
    private TestEntityManager em;

    @DisplayName("Возвращать все жанры")
    @Test
    void shouldReturnAllGenres() {
        val genres = genreRepositoryJpa.getAll();
        assertThat(genres).isNotNull().hasSize(EXPECTED_NUMBER_OF_GENRES);
    }

    @DisplayName("Сохранять жанр")
    @Test
    void shouldSaveGenre() {
        val newGenre = new Genre(0L, TEST_GENRE_NAME);
        genreRepositoryJpa.save(newGenre);
        assertThat(newGenre.getId()).isGreaterThan(0);

        val actualGenre = em.find(Genre.class, newGenre.getId());
        assertThat(actualGenre).isNotNull().matches(genre -> genre.getName().equals(TEST_GENRE_NAME));
    }

    @DisplayName("Возвращать жанр по id")
    @Test
    void shouldReturnGenreById() {
        val actualGenre = genreRepositoryJpa.getById(TEST_GENRE_ID);
        val expectedGenre = Optional.ofNullable(em.find(Genre.class, TEST_GENRE_ID));
        assertThat(actualGenre).usingFieldByFieldValueComparator().isEqualTo(expectedGenre);
    }

    @DisplayName("Возвращать жанр по name")
    @Test
    void shouldReturnGenreByName() {
        val actualGenre = genreRepositoryJpa.getByName(FIRST_GENRE_NAME);
        val expectedGenre = Optional.ofNullable(em.find(Genre.class, FIRST_GENRE_ID));
        assertThat(actualGenre).usingFieldByFieldValueComparator().isEqualTo(expectedGenre);
    }

    @DisplayName("Удалять переданный жанр")
    @Test
    void shouldDeleteGenre() {
        val actualGenre = em.find(Genre.class, FIRST_GENRE_ID);
        assertThat(actualGenre).isNotNull();

        genreRepositoryJpa.delete(actualGenre);
        val deletedGenre = em.find(Genre.class, FIRST_GENRE_ID);

        assertThat(deletedGenre).isNull();
    }
}
