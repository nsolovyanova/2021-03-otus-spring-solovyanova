package ru.otus.spring.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("класс Student")
public class StudentTest {
    @DisplayName("должен корректно создавать новый экземпляр класса Student")
    @Test
    void shouldCorrectConstructorStudent() {
        Student student = new Student("Natalya", "Solovyanova", 36);

        assertThat(student.getFirstName()).isEqualTo("Natalya");
        assertThat(student.getLastName()).isEqualTo("Solovyanova");
        assertThat(student.getAge()).isEqualTo(36);
    }

}
