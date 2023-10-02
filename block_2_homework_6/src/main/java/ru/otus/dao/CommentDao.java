package ru.otus.dao;

import ru.otus.domain.Comment;

import java.util.Optional;

public interface CommentDao {
    Optional<Comment> getCommentById(long id);

    Comment createComment(Comment comment);

    void deleteComment(Comment comment);
}
