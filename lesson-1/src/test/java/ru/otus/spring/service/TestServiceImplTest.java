package ru.otus.spring.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.MessageSource;
import ru.otus.spring.config.ApplicationConfigs;
import ru.otus.spring.domain.Question;
import ru.otus.spring.domain.Student;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;


import static org.mockito.Mockito.*;
import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("тестируем TestServiceImpl")
@SpringBootTest(classes = TestServiceImpl.class)
class TestServiceImplTest {
    private TestService testService;
    private final Student student = new Student("Natalya", "Solovyanova", 36);
    private List<Question> questionList = new ArrayList<>();

    @MockBean
    private StudentService studentService;

    @MockBean
    private QuestionService questionService;

    @MockBean
    private ConsoleReader consoleReader;

    @MockBean
    private ApplicationConfigs applicationConfigs;
    @MockBean
    private MessageSource messageSource;

    private ByteArrayOutputStream out;

    @BeforeEach
    void setUp() {
        testService = new TestServiceImpl(messageSource, applicationConfigs, studentService, questionService, consoleReader);
        Question question1 = new Question(1, "1*1", "1", new ArrayList<String>());
        questionList.add(question1);
    }

    @DisplayName("должен тестировать студента и вывести результат тестирования")
    @Test
    void shouldCorrectGetTestResult() {
        when(studentService.getStudent()).thenReturn(student);
        when(questionService.getQuestions()).thenReturn(questionList);
        when(consoleReader.getNextLine("")).thenReturn("1");
        doReturn(new Locale("en", "US")).when(applicationConfigs).getLocale();
        when(applicationConfigs.getCountSuccessfulAnswers()).thenReturn(1);
        testService.startTest();
        assertThat(student.getResultTest()).isTrue();
    }


}
