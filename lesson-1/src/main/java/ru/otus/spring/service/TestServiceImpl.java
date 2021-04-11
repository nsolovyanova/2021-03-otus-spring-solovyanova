package ru.otus.spring.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;
import ru.otus.spring.domain.Question;
import ru.otus.spring.domain.Student;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

@Service
@RequiredArgsConstructor
@PropertySource("classpath:application.property")
public class TestServiceImpl implements TestService {

    private final StudentService studentService;
    private final QuestionService questionService;
    private List<String> answers = new ArrayList<>();

    @Value("${questions.countsuccessfulanswers}")
    private int count;
    private final ConsoleReader consoleReader;

    @Override
    public void startTest() {

        try {
            Student student = studentService.getStudent();
            List<Question> questions = questionService.getQuestions();
            int rightAnswers = 0;
            for (int i = 0; i < questions.size(); i++) {
                System.out.println("Question № " + questions.get(i).getId());
                System.out.println(questions.get(i).getText());
                questionService.showAnswers(questions.get(i).getAnswers());
                String answer = consoleReader.getNextLine();
                answers.add(answer);
                if (answer.equals(questions.get(i).getRealAnswer())) {
                    rightAnswers++;
                }
            }
            System.out.println("Number of correct answers: " + rightAnswers);

            student.setAnswer(answers);
            if (rightAnswers != count) {
                System.out.println("Unfortunately you didn't pass this test.");
                student.setResultTest(false);
            } else {
                System.out.println("Congratulations, you have successfully passed this test!");
                student.setResultTest(true);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
