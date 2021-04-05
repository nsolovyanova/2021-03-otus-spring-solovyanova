package ru.otus.spring.dao;

import ru.otus.spring.domain.Question;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public interface ParserCsvDao {
    public List<Question> getParseQuestionsFromCsv(InputStream input) throws IOException;
}