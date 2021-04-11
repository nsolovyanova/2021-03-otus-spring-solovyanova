package ru.otus.spring.service;

import ru.otus.spring.domain.Student;

import java.io.IOException;

public interface StudentService {
    public Student getStudent() throws IOException;

}
