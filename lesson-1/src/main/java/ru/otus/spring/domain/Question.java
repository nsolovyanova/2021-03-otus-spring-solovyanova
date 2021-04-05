package ru.otus.spring.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.io.Serializable;
import java.sql.Array;
import java.util.Arrays;

@RequiredArgsConstructor
@Getter
public class Question implements Serializable {
    private int id;
    private String text;
    private String realAnswer;
    private String[] answers;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getRealAnswer() {
        return realAnswer;
    }

    public void setRealAnswer(String realAnswer) {
        this.realAnswer = realAnswer;
    }

    public String[] getAnswers() {
        return answers;
    }

    public void setAnswers(String[] answers) {
        this.answers = answers;
    }

    @Override
    public String toString() {
        return "Question [id=" + id + ", text=" + text + ", realAnswer=" + realAnswer + ", answers="
                + Arrays.toString(answers) + "]";
    }

}
