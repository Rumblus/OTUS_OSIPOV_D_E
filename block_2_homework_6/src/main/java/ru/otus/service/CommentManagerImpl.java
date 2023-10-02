package ru.otus.service;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.dao.BookDao;
import ru.otus.dao.CommentDao;
import ru.otus.domain.Book;
import ru.otus.domain.Comment;
import ru.otus.errors.LibraryErrorCode;

import java.util.Optional;

@AllArgsConstructor
@Service
public class CommentManagerImpl implements CommentManager {

    private final CommentDao commentDao;

    private final BookDao bookDao;

    @Transactional
    @Override
    public LibraryErrorCode createComment(String bookName, String commentData) {
        Optional<Book> bookOpt = bookDao.getBookByName(bookName);

        if (bookOpt.isEmpty()) {
            return LibraryErrorCode.ERR_BOOK_NOT_FOUND;
        }

        commentDao.createComment(new Comment(0, bookOpt.get(), commentData));
        return LibraryErrorCode.ERR_OK;
    }

    @Transactional
    @Override
    public LibraryErrorCode deleteComment(String bookName, int commentId) {
        Optional<Book> bookOpt = bookDao.getBookByName(bookName);

        if (bookOpt.isEmpty()) {
            return LibraryErrorCode.ERR_BOOK_NOT_FOUND;
        }

        commentDao.deleteComment(new Comment(commentId, bookOpt.get(), ""));
        return LibraryErrorCode.ERR_OK;
    }
}
