package by.touchme.newsservice.controller;

import by.touchme.newsservice.cache.Cache;
import by.touchme.newsservice.entity.Comment;
import by.touchme.newsservice.service.CommentService;
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

@WebMvcTest(CommentController.class)
public class CommentControllerUnitTest {

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CommentService commentService;

    @MockBean
    private Cache<Long, Comment> cache;

    @DisplayName("JUnit test for CommentController.getPage")
    @Test
    public void getPage() throws Exception {
        this.mockMvc.perform(
                        get("/v1/comment")
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(status().isOk());
    }

    @DisplayName("JUnit test for CommentController.getById")
    @Test
    public void getById() throws Exception {
        this.mockMvc
                .perform(
                        get("/v1/comment/{id}", 1L)
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(status().isOk());
    }

    @DisplayName("JUnit test for CommentController.create")
    @Test
    public void create() throws Exception {
        Comment createComment = this.getComment();

        Comment createdNews = this.getComment();
        createdNews.setId(1L);
        createdNews.setTime(new Date());

        when(commentService.create(any())).thenReturn(createdNews);

        this.mockMvc.perform(
                        post("/v1/comment")
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(createComment))
                )
                .andDo(print())
                .andExpect(status().isCreated());
    }

    @DisplayName("JUnit test for CommentController.updateById")
    @Test
    public void updateById() throws Exception {
        Comment updateComment = this.getComment();

        Comment updatedComment = this.getComment();
        updatedComment.setId(1L);
        updatedComment.setTime(new Date());

        when(commentService.updateById(any(), any())).thenReturn(updatedComment);

        this.mockMvc.perform(
                        put("/v1/comment/{id}", 1L)
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(updateComment))
                )
                .andDo(print())
                .andExpect(status().isOk());
    }

    @DisplayName("JUnit test for CommentController.deleteById")
    @Test
    public void deleteById() throws Exception {
        this.mockMvc
                .perform(
                        delete("/v1/comment/{id}", 1L)
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(status().isOk());
    }

    private Comment getComment() {
        Comment comment = new Comment();
        comment.setUsername("John Doe");
        comment.setText("Lorem ipsum dolor sit amet, consectetur adipiscing elit.");

        return comment;
    }
}
