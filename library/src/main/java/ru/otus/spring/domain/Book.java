package ru.otus.spring.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@AllArgsConstructor
@Data
@RequiredArgsConstructor
public class Book {
    private Long id;
    private final String name;
    private final Author author;
    private final Genre genre;
}
