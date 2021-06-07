package ru.otus.spring.repositories;

import lombok.val;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.spring.domain.Author;

import java.util.ArrayList;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;


@DisplayName("Репозитория на основе Jpa для работы со авторами AuthorRepositoryJpa должен")
@DataJpaTest
@Import(AuthorRepositoryJpa.class)
public class AuthorRepositoryJpaTest {
    private static final String TEST_AUTHOR_NAME = "Шекспир";
    private static final String FIRST_AUTHOR_NAME = "Агния Барто";
    private static final long FIRST_AUTHOR_ID = 1L;
    private static final long TEST_AUTHOR_ID = 1L;
    private static final int EXPECTED_NUMBER_OF_AUTHORS = 3;

    @Autowired
    private AuthorRepositoryJpa authorRepositoryJpa;

    @Autowired
    private TestEntityManager em;

    @DisplayName("Возвращать всех авторов")
    @Test
    void shouldReturnAllAuthors() {
        val authors = authorRepositoryJpa.getAll();
        assertThat(authors).isNotNull().hasSize(EXPECTED_NUMBER_OF_AUTHORS);
    }

    @DisplayName("Сохранять автора")
    @Test
    void shouldSaveAuthor() {
        val newAuthor = new Author(0L, TEST_AUTHOR_NAME, new ArrayList<>());
        authorRepositoryJpa.save(newAuthor);
        assertThat(newAuthor.getId()).isGreaterThan(0);

        val actualAuthor = em.find(Author.class, newAuthor.getId());
        assertThat(actualAuthor).isNotNull().matches(author -> author.getName().equals(TEST_AUTHOR_NAME));
    }

    @DisplayName("Возвращать автора по id")
    @Test
    void shouldReturnAuthorById() {
        val actualAuthor = authorRepositoryJpa.getById(TEST_AUTHOR_ID);
        val expectedAuthor = Optional.ofNullable(em.find(Author.class, TEST_AUTHOR_ID));
        assertThat(actualAuthor).usingFieldByFieldValueComparator().isEqualTo(expectedAuthor);
    }

    @DisplayName("Возвращать автора по name")
    @Test
    void shouldReturnAuthorByName() {
        val actualAuthor = authorRepositoryJpa.getByName(FIRST_AUTHOR_NAME);
        val expectedAuthor = Optional.ofNullable(em.find(Author.class, FIRST_AUTHOR_ID));
        assertThat(actualAuthor).usingFieldByFieldValueComparator().isEqualTo(expectedAuthor);
    }

    @DisplayName("Удалять переданного автора")
    @Test
    void shouldDeleteAuthor() {
        val actualAuthor = em.find(Author.class, FIRST_AUTHOR_ID);
        assertThat(actualAuthor).isNotNull();

        authorRepositoryJpa.delete(actualAuthor);
        val deletedAuthor = em.find(Author.class, FIRST_AUTHOR_ID);

        assertThat(deletedAuthor).isNull();
    }
}
