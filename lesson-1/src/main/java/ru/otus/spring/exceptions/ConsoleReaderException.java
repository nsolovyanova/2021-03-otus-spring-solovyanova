package ru.otus.spring.exceptions;

public class ConsoleReaderException extends RuntimeException {
    public ConsoleReaderException(String message, Throwable cause) {
        super(message, cause);
    }
}
