package ru.otus.service;

import ru.otus.errors.LibraryErrorCode;

public interface CommentManager {
    LibraryErrorCode createComment(String bookName, String commentData);

    LibraryErrorCode deleteComment(String bookName, int commentId);
}
