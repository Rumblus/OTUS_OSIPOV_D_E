package ru.otus.service;

import ru.otus.domain.Book;
import ru.otus.domain.Comment;
import ru.otus.errors.LibraryErrorCode;

import java.util.List;

public interface CommentManager {
    LibraryErrorCode createComment(String bookName, String commentData);

    LibraryErrorCode deleteComment(String bookName, int commentId);

    List<Comment> getAllBookComments(Book book);
}
