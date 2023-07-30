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
    public void createBook(@ShellOption String title, @ShellOption String authorName, @ShellOption String genreName) {
        title = title.replace(',', ' ');
        authorName = authorName.replace(',', ' ');
        genreName = genreName.replace(',', ' ');

        libraryManager.createBook(title, authorName, genreName);
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
                           @ShellOption String newAuthorName,
                           @ShellOption String newGenreName) {
        newTitle = newTitle.replace(',', ' ');
        newAuthorName = newAuthorName.replace(',', ' ');
        newGenreName = newGenreName.replace(',', ' ');

        libraryManager.updateBook(Long.parseLong(id), newTitle, newAuthorName, newGenreName);
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
