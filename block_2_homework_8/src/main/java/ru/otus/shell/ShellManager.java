package ru.otus.shell;

import lombok.AllArgsConstructor;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;
import ru.otus.domain.Author;
import ru.otus.domain.Book;
import ru.otus.domain.Comment;
import ru.otus.domain.Genre;
import ru.otus.errors.LibraryErrorCode;
import ru.otus.service.LibraryManager;

import java.util.List;

@AllArgsConstructor
@ShellComponent
public class ShellManager {
    private final LibraryManager libraryManager;

    @ShellMethod(key = "createBook")
    public void createBook(@ShellOption String title, @ShellOption String authorName, @ShellOption String genreName) {
        title = title.replace(',', ' ');
        authorName = authorName.replace(',', ' ');
        genreName = genreName.replace(',', ' ');

        Book book = libraryManager.createBook(title, authorName, genreName);
        if (book != null) {
            System.out.println("Book created! ID is: " + book.getId());
        } else {
            System.out.println("An error occured while creating a Book!");
        }
    }

    @ShellMethod(key = "getBookById")
    public void getBookById(@ShellOption String id) {
        Book book = libraryManager.getBookById(id);

        if (book != null) {
            System.out.println("Book\n\tid: " + book.getId() +
                    "\n\ttitle: " + book.getTitle() +
                    "\n\tauthor: " + book.getAuthor().getName() +
                    "\n\tgenre: " + book.getGenre().getName());

            List<Comment> comments = book.getComments();
            if (comments.isEmpty()) {
                return;
            }

            int i = 1;
            System.out.println("\n\tcomments:");
            for (Comment comment : comments) {
                System.out.println("\t" + i++ + ") " + comment.getData() + "\n");
            }
        } else {
            System.out.println("No book with id = " + id + " found!");
        }
    }

    @ShellMethod(key = "getAllBooks")
    public void getAllBooks() {
        List<Book> bookList = libraryManager.getAllBooks();

        if (bookList.isEmpty()) {
            System.out.println("The Library is empty...");
            return;
        }

        for (Book book : bookList) {
            System.out.println("Book\n\tid: " + book.getId() +
                    "\n\ttitle: " + book.getTitle() +
                    "\n\tauthor: " + book.getAuthor().getName() +
                    "\n\tgenre: " + book.getGenre().getName());

            List<Comment> comments = book.getComments();

            if (comments.isEmpty()) {
                continue;
            }

            System.out.println("\n\tcomments:");
            int i = 1;
            for (Comment comment : comments) {
                System.out.println("\t" + i++ + ") " + comment.getData() + "\n");
            }
        }
    }

    @ShellMethod(key = "updateBook")
    public void updateBook(@ShellOption String id,
                           @ShellOption String newTitle,
                           @ShellOption String newAuthorName,
                           @ShellOption String newGenreName) {
        newTitle = newTitle.replace(',', ' ');
        newAuthorName = newAuthorName.replace(',', ' ');
        newGenreName = newGenreName.replace(',', ' ');

        LibraryErrorCode ec = libraryManager.updateBook(id, newTitle, newAuthorName, newGenreName);

        switch (ec) {
            case ERR_BOOK_NOT_FOUND -> System.out.println("No book with id = " + id + " found!");
            case ERR_AUTHOR_NOT_FOUND -> System.out.println("No author with name = " + newAuthorName + " found!");
            case ERR_GENRE_NOT_FOUND -> System.out.println("No genre with name = " + newGenreName + " found!");
            case ERR_OK -> System.out.println("Book was updated!");
        }
    }

    @ShellMethod(key = "deleteBook")
    public void deleteBook(@ShellOption String id) {
        LibraryErrorCode ec = libraryManager.deleteBook(id);

        switch (ec) {
            case ERR_BOOK_NOT_FOUND -> System.out.println("No book with id = " + id + " found!");
            case ERR_OK -> System.out.println("Book was deleted!");
        }
    }

    @ShellMethod(key = "getAllAuthors")
    public void getAllAuthors() {
        List<Author> authorList = libraryManager.getAllAuthors();

        if (authorList.isEmpty()) {
            System.out.println("The are no authors in the library...");
            return;
        }

        for (Author author : authorList) {
            System.out.println("Author\n\tid: " + author.getId() +
                    "\n\tname: " + author.getName() + "\n");
        }
    }

    @ShellMethod(key = "getAllGenres")
    public void getAllGenres() {
        List<Genre> genreList = libraryManager.getAllGenres();

        if (genreList.isEmpty()) {
            System.out.println("The are no genres in the library...");
            return;
        }

        for (Genre genre : genreList) {
            System.out.println("Genre\n\tid: " + genre.getId() +
                    "\n\tname: " + genre.getName() + "\n");
        }
    }

    @ShellMethod(key = "createComment")
    public void createComment(@ShellOption String bookName, @ShellOption String commentData) {
        bookName = bookName.replace(',', ' ');
        commentData = commentData.replace(',', ' ');

        LibraryErrorCode ec = libraryManager.createComment(bookName, commentData);
        switch (ec) {
            case ERR_BOOK_NOT_FOUND -> System.out.println("No book with name = " + bookName + " found!");
            case ERR_OK -> System.out.println("Comment created!");
        }
    }

    @ShellMethod(key = "deleteComment")
    public void deleteComment(@ShellOption String bookName, @ShellOption String commentId) {
        bookName = bookName.replace(',', ' ');

        LibraryErrorCode ec = libraryManager.deleteComment(bookName, Integer.parseInt(commentId));
        switch (ec) {
            case ERR_BOOK_NOT_FOUND -> System.out.println("No book with name = " + bookName + " found!");
            case ERR_OK -> System.out.println("Comment deleted or doesn't exist. Success!");
        }
    }
}
