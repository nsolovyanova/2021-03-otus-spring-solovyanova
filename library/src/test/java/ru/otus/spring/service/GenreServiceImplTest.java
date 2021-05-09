package ru.otus.spring.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.otus.spring.dao.GenreDao;
import ru.otus.spring.domain.Genre;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
@DisplayName("Класс GenreServiceImpl должен")
class GenreServiceImplTest {
    public static final long FIRST_GENRE_ID = 1L;
    public static final String FIRST_GENRE_NAME = "Приключенческий рассказ";
    public static final long SECOND_GENRE_ID = 2L;
    public static final String SECOND_GENRE_NAME = "Ужасы";

    private GenreService genreService;

    @Mock
    private GenreDao dao;

    @BeforeEach
    void setUp() {
        genreService = new GenreServiceImpl(dao);
    }

    @Test
    @DisplayName("создавать нового автора")
    void shouldCreateGenre() {
        Genre expectedGenre = new Genre(FIRST_GENRE_ID, FIRST_GENRE_NAME);
        given(dao.insert(any(Genre.class))).willReturn(expectedGenre);
        Optional<Genre> actualGenre = genreService.createGenre(new Genre(FIRST_GENRE_ID, FIRST_GENRE_NAME));
        verify(dao, times(1)).insert(any(Genre.class));
    }

    @Test
    @DisplayName("возвращать всех авторов")
    void shouldGetListGenre() {
        List<Genre> genres = List.of(new Genre(FIRST_GENRE_ID, FIRST_GENRE_NAME),
                new Genre(SECOND_GENRE_ID, SECOND_GENRE_NAME));
        List<String> expectedGenres = genres.stream().map(Genre::toString).collect(Collectors.toList());
        given(dao.getAll()).willReturn(genres);
        List<String> actualGenres = genreService.getListGenre();

        assertThat(actualGenres).hasSameElementsAs(expectedGenres);
        verify(dao, times(1)).getAll();
    }

}
