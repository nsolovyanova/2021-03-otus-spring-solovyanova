package ru.otus.spring.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.spring.domain.Student;

import java.util.Scanner;

@Service
@RequiredArgsConstructor
public class StudentServiceImpl implements StudentService{

    @Override
    public Student getStudent() {
        String firstName, lastName;
        int age;

        Scanner console = new Scanner(System.in);
        System.out.println("Testing based on the work of Mumu Turgenev");
        System.out.println("Enter your name:");
        firstName = console.nextLine();
        System.out.println("Enter your last name:");
        lastName = console.nextLine();
        System.out.println("Enter your age:");
        age = console.nextInt();
        return new Student(firstName, lastName, age);
    }
}
