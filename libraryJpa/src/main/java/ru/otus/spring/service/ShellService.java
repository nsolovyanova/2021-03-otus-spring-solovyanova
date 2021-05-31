package ru.otus.spring.service;

import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.spring.config.ApplicationConfigs;
import ru.otus.spring.domain.Author;
import ru.otus.spring.domain.Book;
import ru.otus.spring.domain.Genre;

import java.util.List;
import java.util.Optional;

@ShellComponent
@RequiredArgsConstructor
public class ShellService {
    private final GenreService genreService;
    private final AuthorService authorService;
    private final BookService bookService;
    private final MessageSource ms;
    private final ApplicationConfigs applicationConfigs;

    @ShellMethod(value = "Get all authors", key = {"authors", "a"})
    public void getListAuthor() {
        List<Author> authors = authorService.getAll();
        authors.stream().forEach(author -> System.out.println(author.getName()));
    }

    @ShellMethod(value = "Get all genres", key = {"genres", "g"})
    public void getListGenres() {
        genreService.getAll().stream().forEach(genre -> System.out.println(genre.getName()));
    }

    @ShellMethod(value = "Get all book: get", key = {"book", "b"})
    public void getListBook() {
        bookService.getAll().stream().forEach(System.out::println);
    }

    @ShellMethod(value = "Add new author: aa <Author>", key = {"add-author", "aa"})
    public String addAuthor(@ShellOption String authorName) {
        try {
            Author author = authorService.save(new Author(0L, authorName));
            return ms.getMessage("author.added.successfully" + author.getName(), null, applicationConfigs.getLocale());
        } catch (Exception e) {
            return ms.getMessage("author.adding.error", null, applicationConfigs.getLocale());
        }
    }

    @ShellMethod(value = "Add new genre: ag <Genre>", key = {"add-genre", "ag"})
    public String addGenre(@ShellOption String genreName) {
        try {
            Genre genre = genreService.save(new Genre(0L, genreName));
            return ms.getMessage("genre.added.successfully" + genre.getName(), null, applicationConfigs.getLocale());
        } catch (Exception e) {
            return ms.getMessage("genre.adding.error", null, applicationConfigs.getLocale());
        }
    }

    @Transactional
    @ShellMethod(value = "Delete Author: da <Author>", key = {"da"})
    public String authorDelete(@ShellOption(help = "authorName") String name) {
        Optional<Author> author = authorService.getByName(name);
        if (author.isPresent()) {
            authorService.delete(author.get());
            return ms.getMessage("Author has deleted.", null, applicationConfigs.getLocale());
        } else
            return ms.getMessage("There is no Author with this authorName.", null, applicationConfigs.getLocale());
    }

    @Transactional
    @ShellMethod(value = "Delete Genre: dg <Genre>", key = {"dg"})
    public String genreDelete(@ShellOption(help = "genre") String name) {
        Optional<Genre> genre = genreService.getByName(name);
        if (genre.isPresent()) {
            genreService.delete(genre.get());
            return ms.getMessage("Genre has deleted.", null, applicationConfigs.getLocale());
        } else
            return ms.getMessage("There is no Genre with this genreName.", null, applicationConfigs.getLocale());
    }

//    @Transactional
//    @ShellMethod(value = "Create library book: cr <bookName>", key = {"cr", "create"})
//    public String createLibraryBook(@ShellOption(value = "-t", help = "The name of the book") String bookName,
//                                    @ShellOption(value = "-a", help = "Authors of the book") String authorName,
//                                    @ShellOption(value = "-g", help = "Genres of the book") String genreName) {
//
//        try {
//            Author author = authorService.save(new Author(authorName));
//            Genre genre = genreService.save(new Genre(genreName));
//
//            Book book = bookService.insert(Book.builder().id(0).name(bookName).author(author.get()).genre(genre.get()).build());
//
//            return ms.getMessage("book.added.successfully" + book.getName() + ". Author = " + author.getName() + ". Genre = " + genre.getName()
//                    , null, applicationConfigs.getLocale());
//        } catch (Exception e) {
//            return ms.getMessage("book.adding.error", null, applicationConfigs.getLocale());
//        }
//    }
//
//    @ShellMethod(value = "Update library book: up <bookName>", key = {"up", "update"})
//    public String updateLibraryBook(@ShellOption(value = "-i", help = "The id of the book") Long bookId,
//                                    @ShellOption(value = "-t", help = "The name of the book") String bookName,
//                                    @ShellOption(value = "-a", help = "Authors of the book") String authorName,
//                                    @ShellOption(value = "-g", help = "Genres of the book") String genreName) {
//
//        try {
//            Optional<Author> author = authorService.createAuthor(new Author(authorName));
//            Optional<Genre> genre = genreService.createGenre(new Genre(genreName));
//            BookDto book = bookService.updateBook(new Book(bookId, bookName, author.orElse(null), genre.orElse(null)));
//            return ms.getMessage("book.update.successfully", new String[]{book.toString()}, applicationConfigs.getLocale());
//        } catch (Exception e) {
//            return ms.getMessage("book.update.error", null, applicationConfigs.getLocale());
//        }
//    }
//
//    @ShellMethod(value = "Delete a book from library by id: d Num", key = {"del-book", "d"})
//    public String deleteBook(@ShellOption long bookId) {
//        try {
//            bookService.deleteBook(bookId);
//            return ms.getMessage("book.delete.successfully", null, applicationConfigs.getLocale());
//        } catch (Exception e) {
//            return ms.getMessage("book.delete.error", null, applicationConfigs.getLocale());
//        }
//    }

}
