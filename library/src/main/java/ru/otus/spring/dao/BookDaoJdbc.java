package ru.otus.spring.dao;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import ru.otus.spring.domain.Author;
import ru.otus.spring.domain.Book;
import ru.otus.spring.domain.Genre;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class BookDaoJdbc implements BookDao {
    private final NamedParameterJdbcOperations jdbc;

    private static class BookMapper implements RowMapper<Book> {

        @Override
        public Book mapRow(ResultSet resultSet, int i) throws SQLException {
            long id = resultSet.getLong("id");
            String name = resultSet.getString("name");
            Author author = new Author(resultSet.getLong("author_id"), resultSet.getString("author_name"));
            Genre genre = new Genre(resultSet.getLong("genre_id"), resultSet.getString("genre_name"));
            return new Book(id, name, author, genre);
        }
    }

    @Override
    public Book insert(Book book) {
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("name", book.getName());
        params.addValue("author_id", book.getAuthor().getId());
        params.addValue("genre_id", book.getGenre().getId());
        KeyHolder key = new GeneratedKeyHolder();
        jdbc.update("insert into books(name, author_id, genre_id) values (:name, :author_id, :genre_id)", params, key);
        book.setId(key.getKey().longValue());
        return book;
    }

    @Override
    public Book update(Book book) {
        jdbc.update("update books set name = :name, author_id = :author_id, genre_id = :genre_id where id = :id"
                , Map.of("id", book.getId()
                        , "name", book.getName()
                        , "author_id", book.getAuthor().getId()
                        , "genre_id", book.getGenre().getId()
                ));
        return book;
    }

    @Override
    public Optional<Book> getByName(String name) {
        return jdbc.query("select b.id, b.name, " +
                        " a.id as author_id, a.name as author_name, " +
                        " g.id as genre_id, g.name as genre_name, " +
                        " from books b " +
                        " left join authors a on a.id = b.author_id " +
                        " left join genres g on g.id = b.genre_id " +
                        " where b.name = :name",
                Map.of("name", name), new BookDaoJdbc.BookMapper()).stream().findFirst();
    }

    @Override
    public void deleteById(Long id) {
        jdbc.update("delete from books where id = :id", Map.of("id", id));
    }

    @Override
    public Optional<Book> getById(Long id) {
        return jdbc.query("select b.id, b.name, " +
                        " a.id as author_id, a.name as author_name, " +
                        " g.id as genre_id, g.name as genre_name, " +
                        " from books b " +
                        " left join authors a on a.id = b.author_id " +
                        " left join genres g on g.id = b.genre_id " +
                        " where b.id = :id",
                Map.of("id", id), new BookDaoJdbc.BookMapper()).stream().findFirst();
    }

    @Override
    public List<Book> getAll() {
        return jdbc.query("select b.id, b.name, " +
                        " a.id as author_id, a.name as author_name, " +
                        " g.id as genre_id, g.name as genre_name, " +
                        " from books b " +
                        " left join authors a on a.id = b.author_id " +
                        " left join genres g on g.id = b.genre_id",
                new BookDaoJdbc.BookMapper());
    }

//    @Override
//    public List<Book> getAllByAuthorId(long authorId) {
//        return null;
//    }
//
//    @Override
//    public List<Book> getAllByGenreId(long genreId) {
//        return null;
//    }

}
