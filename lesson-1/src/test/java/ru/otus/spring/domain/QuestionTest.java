package ru.otus.spring.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("класс Question")
class QuestionTest {
    @DisplayName("должен корректно создавать новый экземпляр класса Question")
    @Test
    void shouldCorrectConstructorQuestion() {
        Question question = new Question(1, "1+1", "2", new ArrayList<String>());

        assertThat(question.getId()).isEqualTo(1);
        assertThat(question.getText()).isEqualTo("1+1");
        assertThat(question.getRealAnswer()).isEqualTo("2");
    }
}
