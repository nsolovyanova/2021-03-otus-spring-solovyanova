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
    public Student getStudent() throws IOException {
        String firstName;
        String lastName;
        int age;

        System.out.println("Testing based on the work of Mumu Turgenev");
        System.out.println("Enter your name:");

        firstName = consoleReader.getNextLine();
        System.out.println("Enter your last name:");
        lastName = consoleReader.getNextLine();
        System.out.println("Enter your age:");
        age = Integer.valueOf(consoleReader.getNextLine());
        return new Student(firstName, lastName, age);
    }
}
