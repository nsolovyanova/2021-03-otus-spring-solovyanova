package ru.otus.spring.dao;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;
import org.springframework.dao.EmptyResultDataAccessException;
import ru.otus.spring.domain.Author;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatCode;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

@DisplayName("Класс AuthorDaoJdbc должен")
@JdbcTest
@Import(AuthorDaoJdbc.class)
class AuthorDaoJdbcTest {
    public static final String INSERT_AUTHOR_NAME = "Хичкок Альфред";
    public static final String EXISTING_AUTHOR_NAME = "Пушкин А.С.";
    public static final long EXISTING_AUTHOR_ID = 2L;
    public static final String FIRST_AUTHOR_NAME = "Агния Барто";
    public static final long FIRST_AUTHOR_ID = 1L;
    public static final String SECOND_AUTHOR_NAME = "Крапивин Владислав";
    public static final long SECOND_AUTHOR_ID = 2L;

    @Autowired
    private AuthorDao authorDao;

    @Test
    @DisplayName("должен добавлять автора")
    void shouldInsertAuthor() {
        Author newAuthor = new Author(INSERT_AUTHOR_NAME);
        Author insAuthor = authorDao.insert(newAuthor);
        assertThat(insAuthor.getId()).isGreaterThan(0L);
        assertThat(insAuthor.getName()).isEqualTo(newAuthor.getName());
    }

    @Test
    @DisplayName("должен искать автора по id")
    void shouldFindAuthorById() {
        Author expectedAuthor = new Author(EXISTING_AUTHOR_ID, EXISTING_AUTHOR_NAME);
        authorDao.insert(expectedAuthor);
        Author actualAuthor = authorDao.getById(expectedAuthor.getId());
        assertThat(actualAuthor).usingRecursiveComparison().isEqualTo(expectedAuthor);
    }

    @Test
    @DisplayName("должен удалять автора")
    void shouldDeleteAuthor() {
        assertThatCode(() -> authorDao.getById(EXISTING_AUTHOR_ID))
                .doesNotThrowAnyException();

        authorDao.deleteById(EXISTING_AUTHOR_ID);

        assertThatThrownBy(() -> authorDao.getById(EXISTING_AUTHOR_ID))
                .isInstanceOf(EmptyResultDataAccessException.class);
    }

    @Test
    @DisplayName("должен возвращать всех авторов")
    void shouldFindAllAuthors() {
        List<Author> listExpected = new ArrayList<>();
        listExpected.add(new Author(FIRST_AUTHOR_ID, FIRST_AUTHOR_NAME));
        listExpected.add(new Author(SECOND_AUTHOR_ID, SECOND_AUTHOR_NAME));
        listExpected.add(new Author(3L, "Агния Барто2"));
        List<Author> listActual = authorDao.getAll();
        assertThat(listActual.size()).isEqualTo(listExpected.size());
        assertThat(listActual).hasSameElementsAs(listExpected);
    }

    @Test
    @DisplayName("должен находить автора по имени")
    void shouldUpdateAuthor() {
        Author authorExpected = new Author(EXISTING_AUTHOR_ID, EXISTING_AUTHOR_NAME);
        authorDao.insert(authorExpected);
        Optional<Author> authorActual = authorDao.getByName(EXISTING_AUTHOR_NAME);
        assertThat(authorActual.isPresent()).isTrue();
        assertThat(authorActual.get()).isEqualTo(authorExpected);
    }

//    @Test
//    @DisplayName("должен изменять автора")
//    void shouldUpdateAuthor() {
//
//    }
//

}
