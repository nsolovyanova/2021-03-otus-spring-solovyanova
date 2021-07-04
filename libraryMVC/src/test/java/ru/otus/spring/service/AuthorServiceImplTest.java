package ru.otus.spring.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import ru.otus.spring.domain.Author;
import ru.otus.spring.repositories.AuthorRepository;

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
    private AuthorRepository authorRepository;

    @BeforeEach
    void setUp() {
        authorService = new AuthorServiceImpl(authorRepository);
    }

    @Test
    @DisplayName("создавать нового автора")
    void shouldSaveAuthor() {
        Author expectedAuthor = new Author(FIRST_AUTHOR_ID, FIRST_AUTHOR_NAME);
        given(authorRepository.save(any(Author.class))).willReturn(expectedAuthor);
        Author actualAuthor = authorService.save(new Author(FIRST_AUTHOR_ID, FIRST_AUTHOR_NAME));
        verify(authorRepository, times(1)).save(any(Author.class));
    }

    @Test
    @DisplayName("возвращать всех авторов")
    void shouldGetListAuthor() {
        List<Author> authors = List.of(new Author(FIRST_AUTHOR_ID, FIRST_AUTHOR_NAME),
                new Author(SECOND_AUTHOR_ID, SECOND_AUTHOR_NAME));
        List<String> expectedAuthors = authors.stream().map(Author::toString).collect(Collectors.toList());
        given(authorRepository.findAll()).willReturn(authors);
        List<String> actualAuthors = authorService.getAll().stream().map(Author::toString).collect(Collectors.toList());

        assertThat(actualAuthors).hasSameElementsAs(expectedAuthors);
        verify(authorRepository, times(1)).findAll();
    }

    @DisplayName("возвращать автора по id")
    @Test
    void shouldGetById() {
        Author expectedAuthor = new Author(FIRST_AUTHOR_ID, FIRST_AUTHOR_NAME);
        given(authorRepository.findById(FIRST_AUTHOR_ID)).willReturn(Optional.ofNullable(expectedAuthor));
        var author = authorService.getById(FIRST_AUTHOR_ID).orElse(null);

        assertThat(author).isNotNull();
        assertThat(author.getName()).isEqualTo(FIRST_AUTHOR_NAME);
    }

    @DisplayName("возвращать автора по name")
    @Test
    void shouldGetByName() {
        Author expectedAuthor = new Author(FIRST_AUTHOR_ID, FIRST_AUTHOR_NAME);
        given(authorRepository.findByName(FIRST_AUTHOR_NAME)).willReturn(Optional.ofNullable(expectedAuthor));
        var author = authorService.getByName(FIRST_AUTHOR_NAME).orElse(null);

        assertThat(author).isNotNull();
        assertThat(author.getId()).isEqualTo(FIRST_AUTHOR_ID);
    }

}
