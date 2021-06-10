package ru.otus.spring.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.otus.spring.domain.Genre;
import ru.otus.spring.repositories.GenreRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("Класс GenreServiceImpl должен")
class GenreServiceImplTest {
    public static final long FIRST_GENRE_ID = 1L;
    public static final String FIRST_GENRE_NAME = "Драмма";
    public static final long SECOND_GENRE_ID = 2L;
    public static final String SECOND_GENRE_NAME = "Поэзия";

    private GenreService genreService;

    @Mock
    private GenreRepository genreRepository;

    @BeforeEach
    void setUp() {
        genreService = new GenreServiceImpl(genreRepository);
    }

    @Test
    @DisplayName("создавать новый жанр")
    void shouldSaveGenre() {
        Genre expectedGenre = new Genre(FIRST_GENRE_ID, FIRST_GENRE_NAME);
        given(genreRepository.save(any(Genre.class))).willReturn(expectedGenre);
        Genre actualGenre = genreService.save(new Genre(FIRST_GENRE_ID, FIRST_GENRE_NAME));
        verify(genreRepository, times(1)).save(any(Genre.class));
    }

    @Test
    @DisplayName("возвращать все жанры")
    void shouldGetListGenre() {
        List<Genre> genres = List.of(new Genre(FIRST_GENRE_ID, FIRST_GENRE_NAME),
                new Genre(SECOND_GENRE_ID, SECOND_GENRE_NAME));
        List<String> expectedGenres = genres.stream().map(Genre::toString).collect(Collectors.toList());
        given(genreRepository.getAll()).willReturn(genres);
        List<String> actualGenres = genreService.getAll().stream().map(Genre::toString).collect(Collectors.toList());

        assertThat(actualGenres).hasSameElementsAs(expectedGenres);
        verify(genreRepository, times(1)).getAll();
    }

    @DisplayName("возвращать жанр по id")
    @Test
    void shouldGetById() {
        Genre expectedGenre = new Genre(FIRST_GENRE_ID, FIRST_GENRE_NAME);
        given(genreRepository.getById(FIRST_GENRE_ID)).willReturn(Optional.of(expectedGenre));
        Genre genre = genreService.getById(FIRST_GENRE_ID).get();

        assertThat(genre.getName()).isEqualTo(FIRST_GENRE_NAME);
    }

    @DisplayName("возвращать жанр по name")
    @Test
    void shouldGetByName() {
        Genre expectedGenre = new Genre(FIRST_GENRE_ID, FIRST_GENRE_NAME);
        given(genreRepository.getByName(FIRST_GENRE_NAME)).willReturn(Optional.of(expectedGenre));
        Genre genre = genreService.getByName(FIRST_GENRE_NAME).get();

        assertThat(genre.getName()).isEqualTo(FIRST_GENRE_NAME);
    }

}
