package ru.otus.spring.domain;

import lombok.*;

@AllArgsConstructor
@Data
@RequiredArgsConstructor
public class Author {
    private Long id;
    private final String name;

    @Override
    public String toString() {
        return String.format("Author {id = %d, name = '%s'}", id, name);
    }
}
