package ru.otus.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.web.servlet.ModelAndView;
import ru.otus.domain.Author;
import ru.otus.domain.Book;
import ru.otus.domain.Genre;
import ru.otus.dto.BookDto;
import ru.otus.errors.LibraryErrorCode;
import ru.otus.service.LibraryManager;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@WebMvcTest(BookController.class)
public class BookControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private LibraryManager libraryManager;

    @Test
    public void shouldGetAllBooks() throws Exception {
        given(libraryManager.getAllBooks()).willReturn(List.of(
                new Book(1, "Black Arrow", new Author(1, "Robert Lewis Stevenson"), new Genre(1, "Novel"), new ArrayList<>()),
                new Book(2, "Sherlock Holmes", new Author(2, "Lev Tolstoi"), new Genre(2, "Detective"), new ArrayList<>())
        ));

        MvcResult mvcResult = mockMvc.perform(get("/api/books"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andReturn();

        String response = mvcResult.getResponse().getContentAsString();
        ObjectMapper mapper = new ObjectMapper();
        List<BookDto> bookDtoList = mapper.readValue(response, new TypeReference<List<BookDto>>() {});

        List<Book> bookList = new ArrayList<>();
        for (BookDto book : bookDtoList) {
            bookList.add(book.toDomainObject());
        }

        assertThat(bookList).usingRecursiveComparison()
                .ignoringFields("author.id", "genre.id")
                .isEqualTo(libraryManager.getAllBooks());
    }

    @Test
    public void shouldGetBookById() throws Exception {
        given(libraryManager.getBookById(1)).willReturn(new Book(1, "Black Arrow", new Author(1, "Robert Lewis Stevenson"), new Genre(1, "Novel"), new ArrayList<>()));

        MvcResult mvcResult = mockMvc.perform(get("/api/books/1"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andReturn();

        String response = mvcResult.getResponse().getContentAsString();
        ObjectMapper mapper = new ObjectMapper();
        BookDto bookDto = mapper.readValue(response, BookDto.class);

        assertThat(bookDto.toDomainObject()).usingRecursiveComparison()
                .ignoringFields("author.id", "genre.id")
                .isEqualTo(libraryManager.getBookById(1));
    }

    @Test
    public void shouldUpdateBook() throws Exception {
        given(libraryManager.getBookById(1)).willReturn(
                new Book(1, "Black Arrow",
                        new Author(1, "Robert Lewis Stevenson"),
                        new Genre(1, "Novel"),
                        new ArrayList<>()));

        MvcResult mvcResult = mockMvc.perform(post("/api/books/edit")
                                              .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                                              .content("id=1&title=Black+Arrow&author=Robert+Lewis+Stevenson&genre=Novel"))
                .andExpect(status().is3xxRedirection())
                .andReturn();

        ModelAndView modelAndView = mvcResult.getModelAndView();
        assertThat(modelAndView).isNotNull();
    }

    @Test
    public void shouldCreateBook() throws Exception {
        given(libraryManager.createBook(any(String.class), any(String.class), any(String.class))).willReturn(new Book(1, "Black Arrow", new Author(1, "Robert Lewis Stevenson"), new Genre(1, "Novel"), new ArrayList<>()));

        MvcResult mvcResult = mockMvc.perform(post("/api/books/create")
                                              .contentType(MediaType.APPLICATION_JSON)
                                              .content("{\"id\":0,\"title\":\"Black Arrow\",\"authorName\":\"Alexander Pushkin\",\"genreName\":\"Novel\"}"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andReturn();

        String response = mvcResult.getResponse().getContentAsString();
        ObjectMapper mapper = new ObjectMapper();
        BookDto bookDto = mapper.readValue(response, BookDto.class);

        assertThat(bookDto.toDomainObject()).usingRecursiveComparison()
                .ignoringFields("author.id", "genre.id")
                .isEqualTo(libraryManager.createBook("Black Arrow", "Robert Lewis Stevenson", "Novel"));
    }

    @Test
    public void shouldDeleteBook() throws Exception {
        given(libraryManager.deleteBook(1)).willReturn(LibraryErrorCode.ERR_OK);

        mockMvc.perform(post("/api/books/delete/1"))
                .andExpect(status().is3xxRedirection());
    }
}
