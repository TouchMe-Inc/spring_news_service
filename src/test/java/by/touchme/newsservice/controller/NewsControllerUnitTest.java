package by.touchme.newsservice.controller;

import by.touchme.newsservice.dto.NewsDto;
import by.touchme.newsservice.filter.JwtFilter;
import by.touchme.newsservice.service.NewsService;
import by.touchme.newsservice.service.PermissionService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Date;

import static org.mockito.Mockito.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ActiveProfiles(profiles = "test")
@AutoConfigureMockMvc(addFilters = false)
@WebMvcTest(NewsController.class)
public class NewsControllerUnitTest {

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    MockMvc mockMvc;

    @MockBean
    NewsService newsService;

    @MockBean
    PermissionService permissionService;

    @MockBean
    JwtFilter jwtFilter;

    @MockBean
    Authentication authentication;

    @DisplayName("JUnit test for NewsController.getPage")
    @WithMockUser
    @Test
    void getPage() throws Exception {
        mockMvc
                .perform(
                        get("/v1/news")
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(status().isOk());
    }

    @DisplayName("JUnit test for NewsController.getById")
    @WithMockUser
    @Test
    void getById() throws Exception {
        NewsDto firstNews = new NewsDto();
        firstNews.setId(1L);
        firstNews.setTitle("Lorem Ipsum");
        firstNews.setText("Lorem ipsum dolor sit amet, consectetur adipiscing elit.");
        firstNews.setTime(new Date());

        when(newsService.getById(any())).thenReturn(firstNews);

        mockMvc
                .perform(
                        get("/v1/news/{id}", 1L)
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(status().isOk());
    }

    @DisplayName("JUnit test for NewsController.create")
    @WithMockUser
    @Test
    void create() throws Exception {
        NewsDto createNews = new NewsDto();
        createNews.setAuthor("Admin");
        createNews.setTitle("Lorem Ipsum");
        createNews.setText("Lorem ipsum dolor sit amet, consectetur adipiscing elit.");

        NewsDto createdNews = new NewsDto();
        createdNews.setId(1L);
        createdNews.setTitle("Lorem Ipsum");
        createdNews.setText("Lorem ipsum dolor sit amet, consectetur adipiscing elit.");
        createdNews.setTime(new Date());

        when(newsService.create(createNews)).thenReturn(createdNews);

        mockMvc.perform(
                        post("/v1/news")
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(createNews))
                )
                .andDo(print())
                .andExpect(status().isCreated());
    }

    @DisplayName("JUnit test for NewsController.updateById")
    @WithMockUser
    @Test
    void updateById() throws Exception {
        NewsDto updateNews = new NewsDto();
        updateNews.setAuthor("Admin");
        updateNews.setTitle("Lorem Ipsum");
        updateNews.setText("Lorem ipsum dolor sit amet, consectetur adipiscing elit.");

        NewsDto updatedNews = new NewsDto();
        updatedNews.setId(1L);
        updatedNews.setTitle("Lorem Ipsum");
        updatedNews.setText("Lorem ipsum dolor sit amet, consectetur adipiscing elit.");
        updatedNews.setTime(new Date());

        when(newsService.updateById(any(), any())).thenReturn(updatedNews);

        mockMvc
                .perform(
                        put("/v1/news/{id}", 1L)
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(updateNews))
                )
                .andDo(print())
                .andExpect(status().isNoContent());
    }

    @DisplayName("JUnit test for NewsController.deleteById")
    @WithMockUser
    @Test
    void deleteById() throws Exception {
        mockMvc
                .perform(
                        delete("/v1/news/{id}", 1L)
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(status().isOk());
    }
}
