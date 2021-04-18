package ru.otus.spring.exceptions;

public class ParseQuestionsException  extends RuntimeException {
    public ParseQuestionsException(String message, Throwable cause) {
        super(message, cause);
    }
}
