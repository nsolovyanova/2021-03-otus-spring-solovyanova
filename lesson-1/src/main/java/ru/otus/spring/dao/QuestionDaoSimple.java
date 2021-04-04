package ru.otus.spring.dao;

import lombok.RequiredArgsConstructor;
import ru.otus.spring.domain.Question;

@RequiredArgsConstructor
public class QuestionDaoSimple implements QuestionDao {
    private final String questionResourceFile;
    private final ParserCsvDao parser;
    private Question question;

    @Override
    public Question getQuestions() {
        try {
            question = parser.getParseQuestionsFromCsv(getClass().getClassLoader().getResourceAsStream(questionResourceFile));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return question;
    }
}
