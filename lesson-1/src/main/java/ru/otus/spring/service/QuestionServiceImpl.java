package ru.otus.spring.service;

import lombok.RequiredArgsConstructor;
import ru.otus.spring.dao.QuestionDao;
import ru.otus.spring.domain.Question;

@RequiredArgsConstructor
public class QuestionServiceImpl implements QuestionService {
    private final QuestionDao dao;
    private Question questions;

    @Override
    public void runTest() throws Exception {
        questions =  this.dao.getQuestions();

    }

}
