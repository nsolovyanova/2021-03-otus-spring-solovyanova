package ru.otus.spring.exceptions;

public class QuestionsException extends RuntimeException{
    public QuestionsException(String message, Throwable cause) {
        super(message, cause);
    }
}
