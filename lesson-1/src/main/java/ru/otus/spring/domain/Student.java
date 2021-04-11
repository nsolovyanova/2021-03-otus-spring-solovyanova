package ru.otus.spring.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@RequiredArgsConstructor
public class Student {
    private final String firstName;
    private final String lastName;
    private final int age;
    private List<String> answer;
    private Boolean resultTest;

    @Override
    public String toString() {
        return "Student [firstName=" + firstName + ", lastName=" + lastName + ", age=" + age + ", answer=" + answer + ", resultTest=" + resultTest + "]";
    }
}
