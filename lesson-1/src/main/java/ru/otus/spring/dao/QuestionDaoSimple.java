package ru.otus.spring.dao;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.otus.spring.config.ApplicationConfigs;
import ru.otus.spring.domain.Question;
import ru.otus.spring.exceptions.QuestionsException;

import java.util.List;

@Component
@RequiredArgsConstructor
public class QuestionDaoSimple implements QuestionDao {
    private final ApplicationConfigs applicationConfigs;
    private final ParserCsvDao parser;
    private List<Question> question;

    @Override
    public List<Question> getQuestions() {
        try {
            question = parser.getParseQuestionsFromCsv(getClass().getClassLoader().getResourceAsStream(applicationConfigs.getCsvFileName()));
        } catch (Exception e) {
            throw new QuestionsException("Error getting questions.", e);
        }
        return question;
    }

}
