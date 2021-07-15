package ru.otus.spring.exception;

import ru.otus.spring.Application;

public class BookServiceBookNotFoundException extends SpringApplicationException {
    public BookServiceBookNotFoundException(long bookId) {
        super(String.format("The book with ID %d was not found in the database.", bookId));
    }

}
