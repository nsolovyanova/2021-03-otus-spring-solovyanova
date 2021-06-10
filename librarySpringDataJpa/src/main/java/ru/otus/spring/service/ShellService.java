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
import ru.otus.spring.domain.Comment;
import ru.otus.spring.domain.Genre;

import java.util.List;
import java.util.Optional;

@ShellComponent
@RequiredArgsConstructor
public class ShellService {
    private final GenreService genreService;
    private final AuthorService authorService;
    private final BookService bookService;
    private final CommentService commentService;
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

    @Transactional
    @ShellMethod(value = "Add new author: aa <Author>", key = {"add-author", "aa"})
    public String addAuthor(@ShellOption String authorName) {
        try {
            Author author = authorService.save(new Author(0L, authorName));
            return ms.getMessage("author.added.successfully" + author.getName(), null, applicationConfigs.getLocale());
        } catch (Exception e) {
            return ms.getMessage("author.adding.error", null, applicationConfigs.getLocale());
        }
    }

    @Transactional
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
            return ms.getMessage("author.delete.successfully", null, applicationConfigs.getLocale());
        } else
            return ms.getMessage("author.delete.error", null, applicationConfigs.getLocale());
    }

    @Transactional
    @ShellMethod(value = "Delete Genre: dg <Genre>", key = {"dg"})
    public String genreDelete(@ShellOption(help = "genre") String name) {
        Optional<Genre> genre = genreService.getByName(name);
        if (genre.isPresent()) {
            genreService.delete(genre.get());
            return ms.getMessage("genre.delete.successfully", null, applicationConfigs.getLocale());
        } else
            return ms.getMessage("genre.delete.error", null, applicationConfigs.getLocale());
    }

    @Transactional
    @ShellMethod(value = "Create library book: cr <bookName>", key = {"cr", "create"})
    public String createLibraryBook(@ShellOption(value = "-t", help = "The name of the book") String bookName,
                                    @ShellOption(value = "-a", help = "Authors of the book") String authorName,
                                    @ShellOption(value = "-g", help = "Genres of the book") String genreName) {

        try {
            Optional<Author> author = authorService.getByName(authorName);
            Optional<Genre> genre = genreService.getByName(genreName);

            Book book = bookService.insert(Book.builder().id(0).name(bookName).author(author.get()).genre(genre.get()).build());

            return ms.getMessage("book.added.successfully" + book.getName() + ". Author = " + author.get().getName() + ". Genre = " + genre.get().getName()
                    , null, applicationConfigs.getLocale());
        } catch (Exception e) {
            return ms.getMessage("book.adding.error", null, applicationConfigs.getLocale());
        }
    }

    @Transactional
    @ShellMethod(value = "Update library book: up <bookName>", key = {"up", "update"})
    public String updateLibraryBook(@ShellOption(value = "-i", help = "The id of the book") Long bookId,
                                    @ShellOption(value = "-t", help = "The name of the book") String bookName,
                                    @ShellOption(value = "-a", help = "Authors of the book") String authorName,
                                    @ShellOption(value = "-g", help = "Genres of the book") String genreName) {

        try {
            Optional<Author> author = authorService.getByName(authorName);
            Optional<Genre> genre = genreService.getByName(genreName);
            Optional<Book> book = bookService.getById(bookId);
            return ms.getMessage("book.update.successfully", new String[]{book.toString()}, applicationConfigs.getLocale());
        } catch (Exception e) {
            return ms.getMessage("book.update.error", null, applicationConfigs.getLocale());
        }
    }

    @Transactional
    @ShellMethod(value = "Delete a book from library by id: d Num", key = {"del-book", "d"})
    public String deleteBook(@ShellOption long bookId) {
        Optional<Book> book = bookService.getById(bookId);
        try {
            bookService.delete(book.get());
            return ms.getMessage("book.delete.successfully", null, applicationConfigs.getLocale());
        } catch (Exception e) {
            return ms.getMessage("book.delete.error", null, applicationConfigs.getLocale());
        }
    }

    @Transactional(readOnly = true)
    @ShellMethod(value = "Show all comments show by book: sc  <bookId>", key = {"sc"})
    public void showAllCommentsByBook(@ShellOption(help = "bookId") long bookId) {
        Optional<Book> book = bookService.getById(bookId);

        if (book.isPresent())  {
            if (book.get().getComment() != null && book.get().getComment().size() > 0) {
                System.out.println("Comments by book \"" + book.get().getName() + "\":");
                book.get().getComment().forEach(System.out::println);
            }
            else {
                System.out.println("Book \"" + book.get().getName() + "\" doesn't have any comments");
            }
        }
        else
            System.out.println("The book hasn't find by this id = " + bookId);
    }

    @Transactional
    @ShellMethod(value = "Delete comment by id: dc <commentId>", key = {"dc"})
    public String commentDelete(@ShellOption(help = "commentId") long commentId) {
        Optional<Comment> comment = commentService.getById(commentId);
        if (comment.isPresent()) {
            commentService.delete(comment.get());
            return ms.getMessage("comment.delete.successfully", null, applicationConfigs.getLocale());
        } else
            return ms.getMessage("comment.delete.error", null, applicationConfigs.getLocale());
    }

    @Transactional
    @ShellMethod(value = "Add new comment by book: ac <BookId, Comment>", key = {"add-comment", "ac"})
    public String addCommentByBook(@ShellOption(help = "bookId") Long bookId,
                                   @ShellOption(help = "comment") String comment) {
        Optional<Book> book = bookService.getById(bookId);
        try {
            commentService.save(Comment.builder().id(0).book(book.get()).text_comment(comment).build());
            return ms.getMessage("comment.added.successfully" + book.get().getName(), null, applicationConfigs.getLocale());
        } catch (Exception e) {
            return ms.getMessage("comment.adding.error", null, applicationConfigs.getLocale());
        }
    }

}
