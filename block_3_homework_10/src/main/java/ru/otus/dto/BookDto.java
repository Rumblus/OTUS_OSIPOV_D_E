package ru.otus.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.otus.domain.Author;
import ru.otus.domain.Book;
import ru.otus.domain.Comment;
import ru.otus.domain.Genre;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookDto {
    private long id;

    private String title;

    private String authorName;

    private String genreName;

    private List<String> comments;

    public static BookDto toDto(Book book) {
        List<String> comments = new ArrayList<>();
        for (Comment comment : book.getComments()) {
            comments.add(comment.getData());
        }

        return new BookDto(
                book.getId(),
                book.getTitle(),
                book.getAuthor().getName(),
                book.getGenre().getName(),
                comments
        );
    }

    public Book toDomainObject() {
        return new Book(id,
                        title,
                        new Author(1, authorName),
                        new Genre(1, genreName),
                        new ArrayList<>());
    }
}
