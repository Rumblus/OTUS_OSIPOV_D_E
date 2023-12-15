package ru.otus.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.List;

@Document(collection = "books")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Book {
    @Id
    private String id;

    @Field(name = "title")
    private String title;

    @DBRef
    @Field(name = "author")
    private Author author;

    @DBRef
    @Field(name = "genre")
    private Genre genre;

    @Field(name = "comments")
    private List<Comment> comments;

    public Book(String title, Author author, Genre genre, List<Comment> comments) {
        this.title = title;
        this.author = author;
        this.genre = genre;
        this.comments = comments;
    }
}
