package ru.otus.spring.service;

import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import ru.otus.spring.config.ApplicationConfigs;
import ru.otus.spring.domain.Question;
import ru.otus.spring.domain.Student;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TestServiceImpl implements TestService {
    private final MessageSource messageSource;
    private final ApplicationConfigs applicationConfigs;

    private final StudentService studentService;
    private final QuestionService questionService;
    private List<String> answers = new ArrayList<>();

    private final ConsoleReader consoleReader;

    private Boolean getResultTest(int rightAnswers) {
        Boolean testResult = false;
        System.out.println(messageSource.getMessage("number.correct.answer", new String[]{Integer.toString(rightAnswers)}, applicationConfigs.getLocale()));
        if (rightAnswers != applicationConfigs.getCountSuccessfulAnswers()) {
            System.out.println(messageSource.getMessage("result.unsuccess", null, applicationConfigs.getLocale()));
        } else {
            System.out.println(messageSource.getMessage("result.success", null, applicationConfigs.getLocale()));
            testResult = true;
        }
        return testResult;
    }

    @Override
    public void startTest() {

        try {
            System.out.println(messageSource.getMessage("test.start", null, applicationConfigs.getLocale()));
            Student student = studentService.getStudent();
            List<Question> questions = questionService.getQuestions();
            int rightAnswers = 0;
            for (int i = 0; i < questions.size(); i++) {
                System.out.println("Question № " + questions.get(i).getId());
                System.out.println(questions.get(i).getText());
                questionService.showAnswers(questions.get(i).getAnswers());
                String answer = consoleReader.getNextLine("");
                answers.add(answer);
                if (answer.equals(questions.get(i).getRealAnswer())) {
                    rightAnswers++;
                }
            }
            student.setAnswer(answers);
            student.setResultTest(getResultTest(rightAnswers));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
