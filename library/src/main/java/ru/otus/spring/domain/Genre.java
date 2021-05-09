package ru.otus.spring.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@AllArgsConstructor
@Data
@RequiredArgsConstructor
public class Genre {
    private Long id;
    private final String name;

    @Override
    public String toString() {
        return String.format("Genre {id = %d, name = '%s'}", id, name);
    }
}
