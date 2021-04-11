package ru.otus.spring.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.otus.spring.domain.Student;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

@DisplayName("Класс студент")
@ExtendWith(MockitoExtension.class)
public class StudentServiceImplTest {

    @DisplayName("должен возвращать студента")
    @Test
    void shouldReturnStudent() {
        Student student = new Student("Ivan", "Ivanov", 25);

        assertThat(student.getFirstName()).isEqualTo("Ivan");
        assertThat(student.getLastName()).isEqualTo("Ivanov");
        assertThat(student.getAge()).isEqualTo(25);

    }

}
