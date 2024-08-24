package ru.otus.controller;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.web.servlet.ModelAndView;
import ru.otus.config.SecurityConfig;
import ru.otus.domain.Author;
import ru.otus.domain.Book;
import ru.otus.domain.Genre;
import ru.otus.errors.LibraryErrorCode;
import ru.otus.service.LibraryManager;
import ru.otus.service.security.CustomUserDetailsService;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@WebMvcTest(BookController.class)
@Import({SecurityConfig.class})
public class BookControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private LibraryManager libraryManager;

    @MockBean
    private CustomUserDetailsService customUserDetailsService;

    @WithMockUser(value = "user1", authorities = {"USER"})
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

    @WithMockUser(value = "admin", authorities = {"ADMIN"})
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

    @WithMockUser(value = "admin", authorities = {"ADMIN"})
    @Test
    public void shouldPostEditPage() throws Exception {
        mockMvc.perform(post("/edit?id=1")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .content("title=Black+Arrow&author=Robert+Lewis+Stevenson&genre=Novel"))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/"));
    }

    @WithMockUser(value = "admin", authorities = {"ADMIN"})
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

    @WithMockUser(value = "admin", authorities = {"ADMIN"})
    @Test
    public void shouldPostCreatePage() throws Exception {
        given(libraryManager.createBook(any(String.class), any(String.class), any(String.class)))
                .willReturn(new Book(1, "Black Arrow",
                            new Author(1, "Robert Lewis Stevenson"),
                            new Genre(1, "Novel"),
                            new ArrayList<>()));

        mockMvc.perform(post("/create")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .content("title=Black+Arrow&authorName=Robert+Lewis+Stevenson&genreName=Novel"))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/"));
    }

    @WithMockUser(value = "admin", authorities = {"ADMIN"})
    @Test
    public void shouldPostDelete() throws Exception {
        given(libraryManager.deleteBook(1)).willReturn(LibraryErrorCode.ERR_OK);

        mockMvc.perform(post("/delete?id=1")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/"));
    }
    
    @WithAnonymousUser
    @Test
    public void shouldRedirectToLoginPageForUnauthorizedUser() throws Exception {
        given(libraryManager.deleteBook(1)).willReturn(LibraryErrorCode.ERR_OK);

        mockMvc.perform(post("/delete?id=1")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("http://localhost/login"));
    }

    @WithMockUser(username = "user1", authorities = {"USER"}, roles = {"USER"})
    @Test
    public void shouldDenyAccessForRoleMismatch() throws Exception {
        mockMvc.perform(get("/create"))
                .andExpect(status().isForbidden());
    }
}
