package by.touchme.newsservice.controller;

import by.touchme.newsservice.dto.NewsDto;
import by.touchme.newsservice.service.NewsService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Date;

import static org.mockito.Mockito.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ActiveProfiles(profiles = "test")
@WebMvcTest(NewsController.class)
public class NewsControllerUnitTest {

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private NewsService newsService;

    @DisplayName("JUnit test for NewsController.getPage")
    @Test
    public void getPage() throws Exception {
        this.mockMvc.perform(
                        get("/v1/news")
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(status().isOk());
    }

    @DisplayName("JUnit test for NewsController.getById")
    @Test
    public void getById() throws Exception {
        NewsDto firstNews = new NewsDto();
        firstNews.setId(1L);
        firstNews.setTitle("Lorem Ipsum");
        firstNews.setText("Lorem ipsum dolor sit amet, consectetur adipiscing elit.");
        firstNews.setTime(new Date());

        when(newsService.getById(any())).thenReturn(firstNews);

        this.mockMvc
                .perform(
                        get("/v1/news/{id}", 1L)
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(status().isOk());
    }

    @DisplayName("JUnit test for NewsController.create")
    @Test
    public void create() throws Exception {
        NewsDto createNews = new NewsDto();
        createNews.setTitle("Lorem Ipsum");
        createNews.setText("Lorem ipsum dolor sit amet, consectetur adipiscing elit.");

        NewsDto createdNews = new NewsDto();
        createdNews.setId(1L);
        createdNews.setTitle("Lorem Ipsum");
        createdNews.setText("Lorem ipsum dolor sit amet, consectetur adipiscing elit.");
        createdNews.setTime(new Date());

        when(newsService.create(createNews)).thenReturn(createdNews);

        this.mockMvc.perform(
                        post("/v1/news")
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(createNews))
                )
                .andDo(print())
                .andExpect(status().isCreated());
    }

    @DisplayName("JUnit test for NewsController.updateById")
    @Test
    public void updateById() throws Exception {
        NewsDto updateNews = new NewsDto();
        updateNews.setTitle("Lorem Ipsum");
        updateNews.setText("Lorem ipsum dolor sit amet, consectetur adipiscing elit.");

        NewsDto updatedNews = new NewsDto();
        updatedNews.setId(1L);
        updatedNews.setTitle("Lorem Ipsum");
        updatedNews.setText("Lorem ipsum dolor sit amet, consectetur adipiscing elit.");
        updatedNews.setTime(new Date());

        when(newsService.updateById(any(), any())).thenReturn(updatedNews);

        this.mockMvc.perform(
                        put("/v1/news/{id}", 1L)
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(updateNews))
                )
                .andDo(print())
                .andExpect(status().isNoContent());
    }

    @DisplayName("JUnit test for NewsController.deleteById")
    @Test
    public void deleteById() throws Exception {
        this.mockMvc
                .perform(
                        delete("/v1/news/{id}", 1L)
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(status().isOk());
    }
}
