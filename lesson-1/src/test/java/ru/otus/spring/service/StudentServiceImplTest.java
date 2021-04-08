package ru.otus.spring.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.otus.spring.domain.Student;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

@DisplayName("Класс студент")
@ExtendWith(MockitoExtension.class)
public class StudentServiceImplTest {

    @DisplayName("должен возвращать студента")
    @Test
    void shouldReturnStudent(){
        Student student = new Student("Ivanov","Ivan", 25);


    }


}
