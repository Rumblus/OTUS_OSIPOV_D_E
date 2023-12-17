package ru.otus.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.dao.BookDao;
import ru.otus.domain.Book;
import ru.otus.domain.Comment;
import ru.otus.errors.LibraryErrorCode;

import java.util.Optional;

@AllArgsConstructor
@Service
public class CommentManagerImpl implements CommentManager {

    private final BookDao bookDao;

    @Transactional
    @Override
    public LibraryErrorCode createComment(String bookName, String commentData) {
        Optional<Book> bookOpt = bookDao.findByTitle(bookName);

        if (bookOpt.isEmpty()) {
            return LibraryErrorCode.ERR_BOOK_NOT_FOUND;
        }

        Book book = bookOpt.get();
        Comment comment = new Comment(commentData);
        book.getComments().add(comment);
        bookDao.save(book);
        return LibraryErrorCode.ERR_OK;
    }

    @Transactional
    @Override
    public LibraryErrorCode deleteComment(String bookName, int commentId) {
        Optional<Book> bookOpt = bookDao.findByTitle(bookName);

        if (bookOpt.isEmpty()) {
            return LibraryErrorCode.ERR_BOOK_NOT_FOUND;
        }

        Book book = bookOpt.get();
        book.getComments().remove(commentId - 1);
        bookDao.save(book);
        return LibraryErrorCode.ERR_OK;
    }
}
