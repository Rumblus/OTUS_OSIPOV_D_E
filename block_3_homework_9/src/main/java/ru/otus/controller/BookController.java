package ru.otus.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.otus.domain.Book;
import ru.otus.service.LibraryManager;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class BookController {

    private final LibraryManager libraryManager;

    @GetMapping("/")
    public String listPage(Model model) {
        List<Book> books = libraryManager.getAllBooks();
        model.addAttribute("books", books);
        return "list";
    }

    @GetMapping("/edit")
    public String editPage(@RequestParam("id") long id, Model model) {
        Book book = libraryManager.getBookById(id);
        model.addAttribute("book", book);
        return "edit";
    }

    @PostMapping("/edit")
    public String updateBook(@RequestParam("id") String id,
                             @RequestParam("title") String title,
                             @RequestParam("author") String author,
                             @RequestParam("genre") String genre) {
        libraryManager.updateBook(Long.parseLong(id), title, author, genre);
        return "redirect:/";
    }

    @GetMapping("/create")
    public String createBookPage(Model model) {
        model.addAttribute("title", "");
        model.addAttribute("authorName", "");
        model.addAttribute("genreName", "");
        return "create";
    }

    @PostMapping("/create")
    public String createBook(@RequestParam("title") String title,
                             @RequestParam("authorName") String authorName,
                             @RequestParam("genreName") String genreName) {
        libraryManager.createBook(title, authorName, genreName);
        return "redirect:/";
    }

    @PostMapping("/delete")
    public String deleteBook(@RequestParam("id") long id) {
        libraryManager.deleteBook(id);
        return "redirect:/";
    }
}
