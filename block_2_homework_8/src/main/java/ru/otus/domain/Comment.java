package ru.otus.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class Comment {

    private String id;

    private String data;

    public Comment(String data) {
        this.data = data;
    }
}
