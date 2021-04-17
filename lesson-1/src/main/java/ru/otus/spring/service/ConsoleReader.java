package ru.otus.spring.service;

public interface ConsoleReader {
    String getNextLine(String message);

    void close();
}
