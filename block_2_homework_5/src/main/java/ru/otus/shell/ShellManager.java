package ru.otus.shell;

import lombok.AllArgsConstructor;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;
import ru.otus.service.LibraryManager;

@AllArgsConstructor
@ShellComponent
public class ShellManager {
    private final LibraryManager libraryManager;

    @ShellMethod(key = "createBook")
    public void createBook(@ShellOption String title, @ShellOption String authorId, @ShellOption String genreId) {
        libraryManager.createBook(title, Long.parseLong(authorId), Long.parseLong(genreId));
    }

    @ShellMethod(key = "getBookById")
    public void getBookById(@ShellOption String id) {
        libraryManager.getBookById(Long.parseLong(id));
    }

    @ShellMethod(key = "getAllBooks")
    public void getAllBooks() {
        libraryManager.getAllBooks();
    }

    @ShellMethod(key = "updateBook")
    public void updateBook(@ShellOption String id,
                           @ShellOption String newTitle,
                           @ShellOption String newAuthorId,
                           @ShellOption String newGenreId) {
        libraryManager.updateBook(Long.parseLong(id),
                                  newTitle,
                                  Long.parseLong(newAuthorId),
                                  Long.parseLong(newGenreId));
    }

    @ShellMethod(key = "deleteBook")
    public void deleteBook(String id) {
        libraryManager.deleteBook(Long.parseLong(id));
    }

    @ShellMethod(key = "getAllAuthors")
    public void getAllAuthors() {
        libraryManager.getAllAuthors();
    }

    @ShellMethod(key = "getAllGenres")
    public void getAllGenres() {
        libraryManager.getAllGenres();
    }
}
