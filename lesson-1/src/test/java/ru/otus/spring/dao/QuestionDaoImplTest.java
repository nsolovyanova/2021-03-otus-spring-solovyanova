package ru.otus.spring.dao;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.otus.spring.domain.Question;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;

@DisplayName("Вопросы для теста")
@ExtendWith(MockitoExtension.class)
public class QuestionDaoImplTest {
    private QuestionDao dao;

    @Mock
    private ParserCsvDao parser;

    /*
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

     */
    private int id;
    private String text;
    private String realAnswer;
    private String[] answers;

    @Test
    @DisplayName("должен вернуть список вопросов")
    void shouldGetQuestions() {
        dao = new QuestionDaoSimple("QuestionForTest.csv", parser);
        List<Question> questionList = new ArrayList<>();

        questionList.add(new Question(1,"1+1","2",new ArrayList<>()));
        try {
            doReturn(questionList).when(parser).getParseQuestionsFromCsv(any());
            assertThat(dao.getQuestions()).isNotNull();
            assertThat(dao.getQuestions()).hasSize(1);
            assertThat(dao.getQuestions().get(0).getText()).isEqualTo("1+1");
            assertThat(dao.getQuestions().get(0).getRealAnswer()).isEqualTo("2");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
