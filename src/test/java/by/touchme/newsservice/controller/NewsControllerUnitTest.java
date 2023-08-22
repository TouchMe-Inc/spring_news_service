package by.touchme.newsservice.controller;

import by.touchme.newsservice.entity.News;
import by.touchme.newsservice.service.NewsService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Date;

import static org.mockito.Mockito.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

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
        News firstNews = this.getNews();
        firstNews.setId(1L);
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
        News createNews = this.getNews();
        News createdNews = this.getNews();
        createdNews.setId(1L);
        createdNews.setTime(new Date());

        when(newsService.create(any())).thenReturn(createdNews);

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
        News updateNews = this.getNews();
        News updatedNews = this.getNews();
        updatedNews.setId(1L);
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

    private News getNews() {
        News news = new News();
        news.setTitle("Lorem Ipsum");
        news.setText("Lorem ipsum dolor sit amet, consectetur adipiscing elit.");

        return news;
    }
}
