package ru.otus.controller;

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
    public void shouldGetListPage() throws Exception {
        given(libraryManager.getAllBooks()).willReturn(List.of(
                new Book(1, "Black Arrow", new Author(1, "Robert Lewis Stevenson"), new Genre(1, "Novel"), new ArrayList<>()),
                new Book(2, "Sherlock Holmes", new Author(2, "Lev Tolstoi"), new Genre(2, "Detective"), new ArrayList<>())
        ));

        MvcResult mvcResult = mockMvc.perform(get("/"))
                                     .andExpect(status().isOk())
                                     .andExpect(view().name("list"))
                                     .andExpect(content().contentTypeCompatibleWith(MediaType.TEXT_HTML))
                                     .andReturn();

        ModelAndView modelAndView = mvcResult.getModelAndView();
        assertThat(modelAndView).isNotNull();

        assertThat(modelAndView.getModelMap()).containsKey("books");
        assertThat(modelAndView.getModelMap().get("books")).usingRecursiveComparison().isEqualTo(libraryManager.getAllBooks());
    }

    @Test
    public void shouldGetEditPage() throws Exception {
        given(libraryManager.getBookById(1)).willReturn(
                new Book(1, "Black Arrow",
                        new Author(1, "Robert Lewis Stevenson"),
                        new Genre(1, "Novel"),
                        new ArrayList<>()));

        MvcResult mvcResult = mockMvc.perform(get("/edit?id=1"))
                .andExpect(status().isOk())
                .andExpect(view().name("edit"))
                .andExpect(content().contentTypeCompatibleWith(MediaType.TEXT_HTML))
                .andReturn();

        ModelAndView modelAndView = mvcResult.getModelAndView();
        assertThat(modelAndView).isNotNull();

        assertThat(modelAndView.getModelMap()).containsKey("book");
        assertThat(modelAndView.getModelMap().get("book")).usingRecursiveComparison().isEqualTo(libraryManager.getBookById(1));
    }

    @Test
    public void shouldPostEditPage() throws Exception {
        mockMvc.perform(post("/edit?id=1")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .content("title=Black+Arrow&author=Robert+Lewis+Stevenson&genre=Novel"))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/"));
    }

    @Test
    public void shouldGetCreatePage() throws Exception {
        MvcResult mvcResult = mockMvc.perform(get("/create"))
                .andExpect(status().isOk())
                .andExpect(view().name("create"))
                .andExpect(content().contentTypeCompatibleWith(MediaType.TEXT_HTML))
                .andReturn();

        ModelAndView modelAndView = mvcResult.getModelAndView();
        assertThat(modelAndView).isNotNull();
    }

    @Test
    public void shouldPostCreatePage() throws Exception {
        given(libraryManager.createBook(any(String.class), any(String.class), any(String.class)))
                .willReturn(new Book(1, "Black Arrow",
                            new Author(1, "Robert Lewis Stevenson"),
                            new Genre(1, "Novel"),
                            new ArrayList<>()));

        mockMvc.perform(post("/create")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .content("title=Black+Arrow&authorName=Robert+Lewis+Stevenson&genreName=Novel"))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/"));
    }

    @Test
    public void shouldPostDelete() throws Exception {
        given(libraryManager.deleteBook(1)).willReturn(LibraryErrorCode.ERR_OK);

        mockMvc.perform(post("/delete?id=1")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/"));
    }
}
