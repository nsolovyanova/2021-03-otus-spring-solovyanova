package ru.otus.spring.dao;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import ru.otus.spring.domain.Question;
import ru.otus.spring.exceptions.QuestionsException;

import java.util.List;

@Component
public class QuestionDaoSimple implements QuestionDao {
    private final String questionResourceFile;
    private final ParserCsvDao parser;
    private List<Question> question;

    public QuestionDaoSimple(@Value("${questions.filename}") String questionResourceFile, ParserCsvDao parser) {
        this.parser = parser;
        this.questionResourceFile = questionResourceFile;
    }

    @Override
    public List<Question> getQuestions() {
        try {
            question = parser.getParseQuestionsFromCsv(getClass().getClassLoader().getResourceAsStream(questionResourceFile));
        } catch (Exception e) {
            throw new QuestionsException("Error getting questions.", e);
        }
        return question;
    }
}
