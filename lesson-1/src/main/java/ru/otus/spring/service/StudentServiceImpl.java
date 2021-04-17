package ru.otus.spring.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.spring.domain.Student;

import java.io.IOException;

@Service
@RequiredArgsConstructor
public class StudentServiceImpl implements StudentService {
    private final ConsoleReader consoleReader;

    @Override
    public Student getStudent() throws RuntimeException {
        String firstName = "";
        String lastName = "";
        int age = 0;
        try {
            System.out.println("Testing based on the work of Mumu Turgenev");
            firstName = consoleReader.getNextLine("Enter your name:");
            lastName = consoleReader.getNextLine("Enter your last name:");
            age = Integer.valueOf(consoleReader.getNextLine("Enter your age:"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new Student(firstName, lastName, age);
    }
}
