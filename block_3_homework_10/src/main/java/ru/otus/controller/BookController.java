package ru.otus.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;
import ru.otus.domain.Book;
import ru.otus.dto.BookDto;
import ru.otus.errors.LibraryErrorCode;
import ru.otus.service.LibraryManager;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/books")
@RequiredArgsConstructor
public class BookController {
    private final LibraryManager libraryManager;

    @GetMapping("")
    public List<BookDto> getAllBooks() {
        return libraryManager.getAllBooks().stream().map(BookDto::toDto).collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public BookDto getBookById(@PathVariable long id) {
        return BookDto.toDto(libraryManager.getBookById(id));
    }

    @PostMapping("/edit")
    public RedirectView updateBook(@RequestParam("id") String id,
                                   @RequestParam("title") String title,
                                   @RequestParam("author") String author,
                                   @RequestParam("genre") String genre) {
        libraryManager.updateBook(Long.parseLong(id), title, author, genre);
        return new RedirectView("/");
    }

    @PostMapping("/create")
    public BookDto createBook(@RequestBody BookDto bookDto) {
        Book createdBook = libraryManager.createBook(bookDto.getTitle(), bookDto.getAuthorName(),bookDto.getGenreName());
        return BookDto.toDto(createdBook);
    }

    @PostMapping("/delete/{id}")
    public RedirectView deleteBook(@PathVariable("id") long id) {
        LibraryErrorCode err = libraryManager.deleteBook(id);
        return new RedirectView("/");
    }
}
