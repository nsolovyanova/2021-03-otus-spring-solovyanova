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

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;


@DisplayName("Репозитория на основе Jpa для работы со авторами AuthorRepository должен")
@DataJpaTest
class AuthorRepositoryTest {
    private static final String TEST_AUTHOR_NAME = "Шекспир";
    private static final String FIRST_AUTHOR_NAME = "Агния Барто";
    private static final long FIRST_AUTHOR_ID = 1L;
    private static final long TEST_AUTHOR_ID = 1L;
    private static final int EXPECTED_NUMBER_OF_AUTHORS = 3;

    @Autowired
    private AuthorRepository authorRepository;

    @Autowired
    private TestEntityManager em;

    @DisplayName("Возвращать всех авторов")
    @Test
    void shouldReturnAllAuthors() {
        val authors = authorRepository.findAll();
        assertThat(authors).isNotNull().hasSize(EXPECTED_NUMBER_OF_AUTHORS);
    }

    @DisplayName("Сохранять автора")
    @Test
    void shouldSaveAuthor() {
        val newAuthor = new Author(0L, TEST_AUTHOR_NAME);
        authorRepository.save(newAuthor);
        assertThat(newAuthor.getId()).isPositive();

        val actualAuthor = em.find(Author.class, newAuthor.getId());
        assertThat(actualAuthor).isNotNull().matches(author -> author.getName().equals(TEST_AUTHOR_NAME));
    }

    @DisplayName("Возвращать автора по id")
    @Test
    void shouldReturnAuthorById() {
        val actualAuthor = authorRepository.findById(TEST_AUTHOR_ID);
        val expectedAuthor = Optional.ofNullable(em.find(Author.class, TEST_AUTHOR_ID));
        assertThat(actualAuthor).usingFieldByFieldValueComparator().isEqualTo(expectedAuthor);
    }

    @DisplayName("Возвращать автора по name")
    @Test
    void shouldReturnAuthorByName() {
        val authors = authorRepository.findByName(FIRST_AUTHOR_NAME);
        val expAuthors = Optional.ofNullable(em.find(Author.class, FIRST_AUTHOR_ID));
        assertThat(authors).usingFieldByFieldValueComparator().isEqualTo(expAuthors);
    }

    @DisplayName("Удалять переданного автора")
    @Test
    void shouldDeleteAuthor() {
        val actualAuthor = em.find(Author.class, FIRST_AUTHOR_ID);
        assertThat(actualAuthor).isNotNull();

        authorRepository.delete(actualAuthor);
        val deletedAuthor = em.find(Author.class, FIRST_AUTHOR_ID);

        assertThat(deletedAuthor).isNull();
    }
}
