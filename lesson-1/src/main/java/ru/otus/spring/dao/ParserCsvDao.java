package ru.otus.spring.dao;

import ru.otus.spring.domain.Question;

import java.io.InputStream;

public interface ParserCsvDao {
    public Question getParseQuestionsFromCsv(InputStream input);
}