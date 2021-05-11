package ru.otus.spring.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.otus.spring.dao.AuthorDao;
import ru.otus.spring.domain.Author;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("Класс AuthorServiceImpl должен")
class AuthorServiceImplTest {
    public static final long FIRST_AUTHOR_ID = 1L;
    public static final String FIRST_AUTHOR_NAME = "Куприн";
    public static final long SECOND_AUTHOR_ID = 2L;
    public static final String SECOND_AUTHOR_NAME = "Михалков Сергей";

    private AuthorService authorService;

    @Mock
    private AuthorDao dao;

    @BeforeEach
    void setUp() {
        authorService = new AuthorServiceImpl(dao);
    }

    @Test
    @DisplayName("создавать нового автора")
    void shouldCreateAuthor() {
        Author expectedAuthor = new Author(FIRST_AUTHOR_ID, FIRST_AUTHOR_NAME);
        given(dao.insert(any(Author.class))).willReturn(expectedAuthor);
        Optional<Author> actualAuthor = authorService.createAuthor(new Author(FIRST_AUTHOR_ID, FIRST_AUTHOR_NAME));
        verify(dao, times(1)).insert(any(Author.class));
    }

    @Test
    @DisplayName("возвращать всех авторов")
    void shouldGetListAuthor() {
        List<Author> authors = List.of(new Author(FIRST_AUTHOR_ID, FIRST_AUTHOR_NAME),
                new Author(SECOND_AUTHOR_ID, SECOND_AUTHOR_NAME));
        List<String> expectedAuthors = authors.stream().map(Author::toString).collect(Collectors.toList());
        given(dao.getAll()).willReturn(authors);
        List<String> actualAuthors = authorService.getListAuthor();

        assertThat(actualAuthors).hasSameElementsAs(expectedAuthors);
        verify(dao, times(1)).getAll();
    }

}
