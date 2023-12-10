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
import ru.otus.service.LibraryManager;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@WebMvcTest(BookPagesController.class)
public class BookPagesControllerTest {
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
}
