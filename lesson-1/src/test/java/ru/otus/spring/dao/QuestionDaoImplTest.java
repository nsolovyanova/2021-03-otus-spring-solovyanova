package ru.otus.spring.dao;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DisplayName("Вопросы для теста")
public class QuestionDaoImplTest {
    private QuestionDaoSimple questions;
    private ParserCsvDao parse;

    @Test
    @DisplayName("Генерим исключение, если файла не существует")
    void shouldIOExceptionWhenFileNotFound() {
        String questionFile = "test.csv";
        Exception exception = assertThrows(Exception.class, () -> {
            parse = new ParserCsvDaoSimple();
            questions.getQuestions();
        });

        String expectedException = new StringBuilder("File not found ")
                .append(questionFile)
                .append("!")
                .toString();
        String actualException = exception.getMessage();

        assertTrue(actualException.contains(expectedException));
    }

}
