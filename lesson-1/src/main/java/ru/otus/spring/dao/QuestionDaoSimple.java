package ru.otus.spring.dao;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;
import ru.otus.spring.domain.Question;

import java.util.List;

@Repository
public class QuestionDaoSimple implements QuestionDao {
    private final String questionResourceFile;
    private final ParserCsvDao parser;
    private List<Question> question;

    public QuestionDaoSimple(@Value("${questions.filename}")String questionResourceFile, ParserCsvDao parser) {
        this.parser = parser;
        this.questionResourceFile = questionResourceFile;
    }


    @Override
    public List<Question> getQuestions() {
        try {
            question = parser.getParseQuestionsFromCsv(getClass().getClassLoader().getResourceAsStream(questionResourceFile));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return question;
    }
}
