package ru.otus.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
@Data
public class Book {
    private long id;

    private String title;

    private Author author;

    private Genre genre;
}
