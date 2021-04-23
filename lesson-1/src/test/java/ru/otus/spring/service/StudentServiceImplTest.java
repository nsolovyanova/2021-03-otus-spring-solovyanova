package ru.otus.spring.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.MessageSource;
import ru.otus.spring.config.ApplicationConfigs;
import ru.otus.spring.domain.Student;

import java.util.Locale;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Класс студент")
@ExtendWith(MockitoExtension.class)
class StudentServiceImplTest {
    public static final String FIRST_TEST_NAME = "Natalya";
    public static final String LAST_TEST_NAME = "Solovyanova";
    public static final int TEST_AGE = 36;

    @Mock
    private ConsoleReader consoleReader;
    @Mock
    private ApplicationConfigs applicationConfigs;
    @Mock
    private MessageSource messageSource;

    StudentService studentService;

    @BeforeEach
    void setUp() {
        studentService = new StudentServiceImpl(consoleReader, messageSource, applicationConfigs);
        when(applicationConfigs.getLocale()).thenReturn(new Locale("en", "US"));
        when(messageSource.getMessage(eq("enter.your.firstname"), any(), any())).thenReturn("Enter your name:");
        when(messageSource.getMessage(eq("enter.your.lastname"), any(), any())).thenReturn("Enter your last name:");
        when(messageSource.getMessage(eq("enter.your.age"), any(), any())).thenReturn("Enter your age:");
        when(consoleReader.getNextLine("Enter your name:")).thenReturn(FIRST_TEST_NAME);
        when(consoleReader.getNextLine("Enter your last name:")).thenReturn(LAST_TEST_NAME);
        when(consoleReader.getNextLine("Enter your age:")).thenReturn(Integer.toString(TEST_AGE));
    }

    @DisplayName("должен возвращать студента")
    @Test
    void shouldReturnStudent() {
        Student student = studentService.getStudent();
        assertThat(student.getFirstName()).isEqualTo(FIRST_TEST_NAME);
        assertThat(student.getLastName()).isEqualTo(LAST_TEST_NAME);
        assertThat(student.getAge()).isEqualTo(TEST_AGE);

    }

}
