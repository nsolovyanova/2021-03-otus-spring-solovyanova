package ru.otus.spring.dao;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import ru.otus.spring.domain.Genre;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class GenreDaoJdbc implements GenreDao {
    private final NamedParameterJdbcOperations jdbc;

    private static class GenreMapper implements RowMapper<Genre> {

        @Override
        public Genre mapRow(ResultSet resultSet, int i) throws SQLException {
            return new Genre(resultSet.getLong("id"), resultSet.getString("name"));
        }
    }

    @Override
    public Genre insert(Genre genre) {
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("name", genre.getName());
        KeyHolder key = new GeneratedKeyHolder();
        jdbc.update("insert into genres(name) values (:name)", params, key);
        genre.setId(key.getKey().longValue());
        return genre;
    }

    @Override
    public void update(Genre genre) {
        jdbc.update("update genres set name = :name where id = :id", Map.of("id", genre.getId()));
    }

    @Override
    public Optional<Genre> getByName(String name) {
        return jdbc.query("select id, name from genres where name = :name",
                Map.of("name", name), new GenreDaoJdbc.GenreMapper()).stream().findFirst();
    }

    @Override
    public void deleteById(Long id) {
        jdbc.update("delete from genres where id = :id", Map.of("id", id));
    }

    @Override
    public Genre getById(Long id) {
        Map<String, Object> params = Collections.singletonMap("id", id);
        return jdbc.queryForObject("select id, name from genres where id = :id", params, new GenreDaoJdbc.GenreMapper());
    }

    @Override
    public List<Genre> getAll() {
        return jdbc.query("select id, name from genres", new GenreDaoJdbc.GenreMapper());
    }

//    @Override
//    public List<Genre> getListByGenreId(long GenreId) {
//        return null;
//    }

}
