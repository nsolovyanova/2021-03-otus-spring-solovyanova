package ru.otus.spring.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class BookDto {
    private long id;
    private String name;
    private AuthorDto authorDto;
    private GenreDto genreDto;
    private List<CommentDto> commentDtoList;

}
