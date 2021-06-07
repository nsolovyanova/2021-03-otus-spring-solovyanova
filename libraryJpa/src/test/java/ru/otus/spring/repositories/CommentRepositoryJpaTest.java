package ru.otus.spring.repositories;

import lombok.val;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;
import ru.otus.spring.domain.Author;
import ru.otus.spring.domain.Book;
import ru.otus.spring.domain.Comment;
import ru.otus.spring.domain.Genre;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Тест репозитория на основе Jpa для работы со комментариями книг BookCommentRepositoryJpa")
@DataJpaTest
@Import(CommentRepositoryJpa.class)
public class CommentRepositoryJpaTest {
    private static final long TEST_COMMENT_ID = 1L;
    private static final String TEST_COMMENT_NAME = "Самая лучшая книга";
    private static final String TEST_AUTHER_NAME = "Стивен Кинг";
    private static final String TEST_GENRE_NAME = "Фантастика";
    private static final String TEST_BOOK_NAME = "Оно";

    private static final String FIRST_COMMENT_NAME = "Класс книга";
    private static final long FIRST_COMMENT_ID = 1L;
    private static final int EXPECTED_NUMBER_OF_COMMENTS = 2;

    @Autowired
    private CommentRepositoryJpa  commentRepositoryJpa;

    @Autowired
    private TestEntityManager em;

//    @DisplayName("Возвращать все комментарии по книге")
//    @Test
//    void shouldReturnAllComments() {
//
//    }

    @DisplayName("Сохранять комментарий")
    @Test
    void shouldSaveComment() {
        val author = new Author(0L, TEST_AUTHER_NAME, new ArrayList<>());
        val genre = new Genre(0L, TEST_GENRE_NAME);
        val book = Book.builder().id(0).name(TEST_BOOK_NAME).author(author).genre(genre).build();
        val comment = new Comment(1L, book, TEST_COMMENT_NAME);

        commentRepositoryJpa.save(comment);
        assertThat(comment.getId()).isGreaterThan(0);

        val actualComment = em.find(Comment.class, comment.getId());
        assertThat(actualComment).isNotNull().matches(c -> c.getText_comment().equals(TEST_COMMENT_NAME));
    }

    @DisplayName("Возвращать комментарий по id")
    @Test
    void shouldReturnCommentById() {
        val actualComment = commentRepositoryJpa.getById(TEST_COMMENT_ID);
        val expectedComment = Optional.ofNullable(em.find(Comment.class, TEST_COMMENT_ID));
        assertThat(actualComment).usingFieldByFieldValueComparator().isEqualTo(expectedComment);
    }

    @DisplayName("Удалять комментарий по книге")
    @Test
    void shouldDeleteComment() {
        val actualComment = em.find(Comment.class, FIRST_COMMENT_ID);
        assertThat(actualComment).isNotNull();

        commentRepositoryJpa.delete(actualComment);
        val deletedComment = em.find(Comment.class, FIRST_COMMENT_ID);

        assertThat(deletedComment).isNull();
    }
}
