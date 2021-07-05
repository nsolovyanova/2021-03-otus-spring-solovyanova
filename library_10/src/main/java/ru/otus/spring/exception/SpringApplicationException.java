package ru.otus.spring.exception;

public class SpringApplicationException extends RuntimeException {
    public SpringApplicationException(String message) {
        super(message);
    }
}
