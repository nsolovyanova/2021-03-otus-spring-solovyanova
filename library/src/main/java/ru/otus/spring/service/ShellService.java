package ru.otus.spring.service;

import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;
import ru.otus.spring.config.ApplicationConfigs;
import ru.otus.spring.domain.Author;
import ru.otus.spring.domain.Book;
import ru.otus.spring.domain.Genre;
import ru.otus.spring.dto.BookDto;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@ShellComponent
@RequiredArgsConstructor
public class ShellService {
    private final GenreService genreService;
    private final AuthorService authorService;
    private final BookService bookService;
    private final MessageSource ms;
    private final ApplicationConfigs applicationConfigs;

    @ShellMethod(value = "Get list authors", key = {"authors", "a"})
    public List<String> getListAuthor() {
        return authorService.getListAuthor();
    }

    @ShellMethod(value = "Get list genres", key = {"genres", "g"})
    public List<String> getListGenres() {
        return genreService.getListGenre();
    }

    @ShellMethod(value = "Get list book: get", key = {"book", "b"})
    public List<String> getListBook() {
        return bookService.getListBook()
                .stream()
                .map(BookDto::toString)
                .collect(Collectors.toList());
    }

    @ShellMethod(value = "Add new author: aa <Author>", key = {"add-author", "aa"})
    public String addAuthor(@ShellOption String authorName) {
        try {
            Optional<Author> author = authorService.createAuthor(new Author(authorName));
            return ms.getMessage("author.added.successfully", new String[]{author.toString()}, applicationConfigs.getLocale());
        } catch (Exception e) {
            return ms.getMessage("author.adding.error", null, applicationConfigs.getLocale());
        }
    }

    @ShellMethod(value = "Add new genre: ag <Genre>", key = {"add-genre", "ag"})
    public String addGenre(@ShellOption String genreName) {
        try {
            Optional<Genre> genre = genreService.createGenre(new Genre(genreName));
            return ms.getMessage("genre.added.successfully", new String[]{genre.toString()}, applicationConfigs.getLocale());
        } catch (Exception e) {
            return ms.getMessage("genre.adding.error", null, applicationConfigs.getLocale());
        }
    }

    @ShellMethod(value = "Create library book: cr <bookName>", key = {"cr", "create"})
    public String createLibraryBook(@ShellOption(value = "-t", help = "The name of the book") String bookName,
                                    @ShellOption(value = "-a", help = "Authors of the book") String authorName,
                                    @ShellOption(value = "-g", help = "Genres of the book") String genreName) {

        try {
            Optional<Author> author = authorService.createAuthor(new Author(authorName));
            Optional<Genre> genre = genreService.createGenre(new Genre(genreName));
            BookDto book = bookService.createBook(new Book(bookName, author.orElse(null), genre.orElse(null)));
            return ms.getMessage("book.added.successfully", new String[]{book.toString()}, applicationConfigs.getLocale());
        } catch (Exception e) {
            return ms.getMessage("book.adding.error", null, applicationConfigs.getLocale());
        }
    }

    @ShellMethod(value = "Update library book: up <bookName>", key = {"up", "update"})
    public String updateLibraryBook(@ShellOption(value = "-i", help = "The id of the book") Long bookId,
                                    @ShellOption(value = "-t", help = "The name of the book") String bookName,
                                    @ShellOption(value = "-a", help = "Authors of the book") String authorName,
                                    @ShellOption(value = "-g", help = "Genres of the book") String genreName) {

        try {
            Optional<Author> author = authorService.createAuthor(new Author(authorName));
            Optional<Genre> genre = genreService.createGenre(new Genre(genreName));
            BookDto book = bookService.updateBook(new Book(bookId, bookName, author.orElse(null), genre.orElse(null)));
            return ms.getMessage("book.update.successfully", new String[]{book.toString()}, applicationConfigs.getLocale());
        } catch (Exception e) {
            return ms.getMessage("book.update.error", null, applicationConfigs.getLocale());
        }
    }

    @ShellMethod(value = "Delete a book from library by id: d Num", key = {"del-book", "d"})
    public String deleteBook(@ShellOption long bookId) {
        try {
            bookService.deleteBook(bookId);
            return ms.getMessage("book.delete.successfully", null, applicationConfigs.getLocale());
        } catch (Exception e) {
            return ms.getMessage("book.delete.error", null, applicationConfigs.getLocale());
        }
    }

}
