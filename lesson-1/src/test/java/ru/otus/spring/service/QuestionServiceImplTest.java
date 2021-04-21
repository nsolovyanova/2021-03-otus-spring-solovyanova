package ru.otus.spring.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.otus.spring.dao.QuestionDao;
import ru.otus.spring.domain.Question;
import ru.otus.spring.exceptions.QuestionsException;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
@DisplayName("Класс Сервис вопросов")
public class QuestionServiceImplTest {

    private QuestionService questionService;

    @Mock
    private QuestionDao dao;

    @BeforeEach
    void setUp() {
        List<Question> questions = new ArrayList<>();
        questions.add(new Question(1, "2+2", "4", new ArrayList<>()));
        try {
            when(dao.getQuestions()).thenReturn(questions);
        } catch (Exception e) {
            throw new QuestionsException("Error getting questions.", e);
        }
    }

    @Test
    @DisplayName("должен получить список вопросов")
    void shouldGetQuestions() {
        try {
            questionService = new QuestionServiceImpl(dao);
            assertThat(questionService.getQuestions()).isNotNull();
        } catch (Exception e) {
            throw new QuestionsException("Error getting questions.", e);
        }
    }

}