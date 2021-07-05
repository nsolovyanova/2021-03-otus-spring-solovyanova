package ru.otus.spring.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.bind.annotation.ExceptionHandler;
import ru.otus.spring.dto.AuthorDto;
import ru.otus.spring.dto.BookDto;
import ru.otus.spring.dto.CommentDto;
import ru.otus.spring.dto.GenreDto;
import ru.otus.spring.exception.BookServiceBookNotFoundException;

import java.util.List;

import static org.junit.Assert.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@DisplayName("Класс BookController должен ")
class BookControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @WithMockUser(
            username = "admin",
            authorities = {"ROLE_ADMIN"}
    )
    @Test
    @DisplayName("вернуть все книги.")
    void shouldGetAllBooks() throws Exception {
        var authorDto1 = new AuthorDto(1, "Агния Барто");
        var authorDto2 = new AuthorDto(2, "Крапивин Владислав");
        var genreDto1 = new GenreDto(1, "Детская литература");
        var genreDto2 = new GenreDto(2, "Рассказ");
        var bookDto1 = BookDto.builder().id(1).name("Стихи").authorDto(authorDto1).genreDto(genreDto1).build();
        var bookDto2 = BookDto.builder().id(2).name("Мальчик со шпагой").authorDto(authorDto2).genreDto(genreDto2).build();
        var expectedBooks = List.of(bookDto1, bookDto2);

        mockMvc.perform(get("/spring/books")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(expectedBooks)));
    }

    @WithMockUser(
            username = "admin",
            authorities = {"ROLE_ADMIN"}
    )
    @Test
    @DisplayName("вернуть книгу по id")
    void shouldGetBookById() throws Exception {
        var authorDto2 = new AuthorDto(2, "Крапивин Владислав");
        var genreDto2 = new GenreDto(2, "Рассказ");
        var expectedBook = BookDto.builder().id(2).name("Мальчик со шпагой").authorDto(authorDto2).genreDto(genreDto2).build();

        mockMvc.perform(get("/spring/books/2")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(expectedBook)));
    }

    @WithMockUser(
            username = "admin",
            authorities = {"ROLE_ADMIN"}
    )
    @Test
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
    @DisplayName("добавить книгу")
    void shouldAddBook() throws Exception {
        var authorDtoNew = new AuthorDto(4, "Стивен Кинг");
        var genreDtoNew = new GenreDto(4, "Фантастика");
        var bookNew = BookDto.builder().id(3).name("Темная башня").authorDto(authorDtoNew).genreDto(genreDtoNew).build();
        var expectedBook = BookDto.builder().id(3).name("Темная башня").authorDto(authorDtoNew).genreDto(genreDtoNew).build();

        mockMvc.perform(post("/spring/books")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(bookNew)))
                .andExpect(status().isOk());

        mockMvc.perform(get("/spring/books/3")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(expectedBook)));
    }

    @WithMockUser(
            username = "admin",
            authorities = {"ROLE_ADMIN"}
    )
    @Test
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
    @DisplayName("изменить книгу")
    void editBook() throws Exception {
        var authorDto2 = new AuthorDto(2, "Крапивин Владислав");
        var genreDto2 = new GenreDto(2, "Рассказ");
        var genreDtoNew = new GenreDto(2, "Повесть");
        var book = BookDto.builder().id(2).name("Мальчик со шпагой").authorDto(authorDto2).genreDto(genreDto2).build();
        var updatedBook = BookDto.builder().id(2).name("Мальчик со шпагой").authorDto(authorDto2).genreDto(genreDtoNew).build();

        mockMvc.perform(get("/spring/books/2")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(book)));

        mockMvc.perform(put("/spring/books/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updatedBook)))
                .andExpect(status().isOk());

        mockMvc.perform(get("/spring/books/2")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(updatedBook)));
    }

    @WithMockUser(
            username = "admin",
            authorities = {"ROLE_ADMIN"}
    )
    @Test
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
    @DisplayName("удалить книгу")
    void shouldRemoveBook() throws Exception {
        var authorDto2 = new AuthorDto(2, "Крапивин Владислав");
        var genreDto2 = new GenreDto(2, "Рассказ");
        var book = BookDto.builder().id(2).name("Мальчик со шпагой").authorDto(authorDto2).genreDto(genreDto2).build();

        mockMvc.perform(get("/spring/books/2")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(book)));

        mockMvc.perform(delete("/spring/books/2")).andExpect(status().isOk());

        mockMvc.perform(get("/spring/books/2")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(result -> assertTrue(
                        result.getResolvedException() instanceof BookServiceBookNotFoundException));
    }

    @WithMockUser(
            username = "admin",
            authorities = {"ROLE_ADMIN"}
    )
    @Test
    @DisplayName("вернуть отзывы о книге по ее идентификатору")
    void shouldGetComments() throws Exception {
        var authorDto1 = new AuthorDto(1, "Агния Барто");
        var genreDto1 = new GenreDto(1, "Детская литература");
        var commentDto1 = new CommentDto(1, "Класс книга");
        var commentDto2 = new CommentDto(2, "Не очень книга");
        var comments = List.of(commentDto1, commentDto2);
        var bookDto1 = BookDto.builder().id(1).name("Стихи").authorDto(authorDto1).genreDto(genreDto1).commentDtoList(comments).build();

        mockMvc.perform(get("/spring/books/1/comments")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(comments)));
    }

}
