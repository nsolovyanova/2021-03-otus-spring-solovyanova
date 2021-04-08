package ru.otus.spring.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.io.Serializable;
import java.sql.Array;
import java.util.Arrays;
import java.util.List;

@RequiredArgsConstructor
@Getter
public class Question implements Serializable {
    private final int id;
    private final String text;
    private final String realAnswer;
    private final List<String> answers;

    @Override
    public String toString() {
        return "Question [id=" + id + ", text=" + text + ", realAnswer=" + realAnswer + ", answers="
                + answers + "]";
    }

}
