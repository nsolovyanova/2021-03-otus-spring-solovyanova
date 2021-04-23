package ru.otus.spring.service;

import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import ru.otus.spring.config.ApplicationConfigs;
import ru.otus.spring.domain.Student;

@Service
@RequiredArgsConstructor
public class StudentServiceImpl implements StudentService {
    private final ConsoleReader consoleReader;
    private final MessageSource messageSource;
    private final ApplicationConfigs applicationConfigs;

    @Override
    public Student getStudent() throws RuntimeException {
        String firstName = "";
        String lastName = "";
        int age = 0;
        try {
            firstName = consoleReader.getNextLine(messageSource.getMessage("enter.your.firstname", null, applicationConfigs.getLocale()));
            lastName = consoleReader.getNextLine(messageSource.getMessage("enter.your.lastname", null, applicationConfigs.getLocale()));
            age = Integer.valueOf(consoleReader.getNextLine(messageSource.getMessage("enter.your.age", null, applicationConfigs.getLocale())));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new Student(firstName, lastName, age);
    }
}
