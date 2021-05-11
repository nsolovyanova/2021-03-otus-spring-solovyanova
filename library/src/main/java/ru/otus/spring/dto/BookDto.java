package ru.otus.spring.dto;

import ru.otus.spring.domain.Book;

public class BookDto {
    private final long num;
    private final String name;
    private final String author;
    private final String genre;

    public BookDto(Book book) {
        num = book.getId();
        name = book.getName();
        author = book.getAuthor().getName();
        genre = book.getGenre().getName();
    }

    @Override
    public String toString() {
        return String.format("%d. %s, %s, %s", num, name, author, genre);
    }

}
