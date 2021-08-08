package ru.otus.spring.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import ru.otus.spring.domain.Author;
import org.springframework.security.test.context.support.WithMockUser;

import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@DisplayName("Класс AuthorController должен ")
class AuthorControllerTest {
    public static final String READER_CREDENTIALS = "cmVhZGVyOnBhc3M=";

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @WithMockUser(
            username = "admin",
            authorities = {"ROLE_ADMIN"}
    )
    @Test
    @DisplayName("вернуть всех авторов.")
    void shouldGetAllAuthors() throws Exception {
        var expectedAuthors = List.of(new Author(1, "Агния Барто"), new Author(2, "Крапивин Владислав"), new Author(3, "Агния Барто2"));

        var result = mockMvc.perform(post("/token")
                .header("Authorization", "Basic " + READER_CREDENTIALS))
                .andExpect(status().isOk())
                .andReturn();
        var token = result.getResponse().getContentAsString();

        mockMvc.perform(get("/spring/authors")
                .header("Authorization", "Bearer " + token)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(expectedAuthors)));
    }

}
