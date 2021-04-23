package ru.otus.spring.dao;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.otus.spring.config.ApplicationConfigs;
import ru.otus.spring.domain.Question;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;

@DisplayName("Вопросы для теста")
@ExtendWith(MockitoExtension.class)
public class QuestionDaoSimpleTest {
    private QuestionDao dao;

    @Mock
    private ParserCsvDao parser;

    @Mock
    private ApplicationConfigs applicationConfigs;

    @Test
    @DisplayName("должен вернуть список вопросов")
    void shouldGetQuestions() {
        dao = new QuestionDaoSimple(applicationConfigs, parser);
        List<Question> questionList = new ArrayList<>();

        questionList.add(new Question(1, "1+1", "2", new ArrayList<>()));
        try {
            doReturn(questionList).when(parser).getParseQuestionsFromCsv(any());
            doReturn("QuestionForTest.csv").when(applicationConfigs).getCsvFileName();
            assertThat(dao.getQuestions()).isNotNull();
            assertThat(dao.getQuestions()).hasSize(1);
            assertThat(dao.getQuestions().get(0).getText()).isEqualTo("1+1");
            assertThat(dao.getQuestions().get(0).getRealAnswer()).isEqualTo("2");
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
