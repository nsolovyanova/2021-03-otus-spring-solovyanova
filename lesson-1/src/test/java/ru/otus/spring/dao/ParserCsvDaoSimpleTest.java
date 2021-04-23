package ru.otus.spring.dao;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.otus.spring.domain.Question;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;


@DisplayName("Класс Парсер из csv")
class ParserCsvDaoSimpleTest {
    private final ParserCsvDaoSimple parser = new ParserCsvDaoSimple();
    private List<Question> questions;

    @BeforeEach
    void setUp() {
        try {
            questions = parser.getParseQuestionsFromCsv(getClass().getClassLoader().getResourceAsStream("QuestionForTest.csv"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    @DisplayName("должен парсить csv")
    void shouldParseResourceCorrectly() {
        assertAll(
                () -> assertThat(questions).isNotNull(),
                () -> assertThat(questions).hasSize(2),
                () -> assertThat(questions.get(1).getAnswers().size()).isEqualTo(4)
        );
    }
}
