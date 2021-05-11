package ru.otus.spring.dao;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import ru.otus.spring.domain.Author;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class AuthorDaoJdbc implements AuthorDao {
    private final NamedParameterJdbcOperations jdbc;

    private static class AuthorMapper implements RowMapper<Author> {

        @Override
        public Author mapRow(ResultSet resultSet, int i) throws SQLException {
            return new Author(resultSet.getLong("id"), resultSet.getString("name"));
        }
    }

    @Override
    public Author insert(Author author) {
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("name", author.getName());
        KeyHolder key = new GeneratedKeyHolder();
        jdbc.update("insert into authors (name) values (:name)", params, key);
        author.setId(key.getKey().longValue());
        return author;
    }

    @Override
    public void update(Author author) {
        jdbc.update("update authors set name = :name where id = :id", Map.of("id", author.getId()));
    }

    @Override
    public Optional<Author> getByName(String name) {
        return jdbc.query("select id, name from authors where name = :name",
                Map.of("name", name), new AuthorMapper()).stream().findFirst();
    }

    @Override
    public void deleteById(Long id) {
        jdbc.update("delete from authors where id = :id", Map.of("id", id));
    }

    @Override
    public Author getById(Long id) {
        return jdbc.queryForObject("select id, name from authors where id = :id", Map.of("id", id), new AuthorMapper());
    }

    @Override
    public List<Author> getAll() {
        return jdbc.query("select id, name from authors", new AuthorMapper());
    }

    @Override
    public List<Author> getListByBookId(long bookId) {
        return jdbc.query("select id, name from authors where book_id = :book_Id", Map.of("book_Id", bookId), new AuthorMapper());
    }

}
