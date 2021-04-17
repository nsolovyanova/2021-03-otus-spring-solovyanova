package ru.otus.spring.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.otus.spring.domain.Question;
import ru.otus.spring.domain.Student;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


import static org.mockito.Mockito.*;
import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
@DisplayName("тестируем TestServiceImpl")
public class TestServiceImplTest {
    private TestServiceImpl testService;
    private final Student student = new Student("Natalya", "Solovyanova", 36);
    private List<Question> questionList = new ArrayList<>();

    @Mock
    private StudentService studentService;

    @Mock
    private QuestionService questionService;

    @Mock
    private ConsoleReader consoleReader;

    private ByteArrayOutputStream out;

    @BeforeEach
    void setUp(){
        out = new ByteArrayOutputStream();
        testService = new TestServiceImpl(studentService, questionService, consoleReader);
        Question question1 = new Question(1,"1*1", "1", new ArrayList<String>());
        Question question2 = new Question(2,"2*2", "2", new ArrayList<String>());
        questionList.add(question1);
        questionList.add(question2);
    }

    @DisplayName("должен тестировать студента и вывести результат тестирования")
    @Test
    void shouldCorrectGetTestResult() throws IOException {
        List<String> answer = new ArrayList<>();
        when(studentService.getStudent()).thenReturn(student);
        when(questionService.getQuestions()).thenReturn(questionList);
        when(consoleReader.getNextLine(questionList.get(1).getText())).thenReturn("1");
        when(consoleReader.getNextLine(questionList.get(2).getText())).thenReturn("2");
        answer.add("1");
        answer.add("2");
        testService.startTest();
        assertThat(out.toString()).endsWith("Congratulations, you have successfully passed this test!\r\n");
    }



}
