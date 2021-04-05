package ru.otus.spring.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.spring.dao.QuestionDao;
import ru.otus.spring.domain.Question;

import java.util.List;

@Service
@RequiredArgsConstructor
public class QuestionServiceImpl implements QuestionService {
    private final QuestionDao dao;

    @Override
    public List<Question> runTest() throws Exception {
        System.out.println(dao.getQuestions());
        return dao.getQuestions();

    }

}
