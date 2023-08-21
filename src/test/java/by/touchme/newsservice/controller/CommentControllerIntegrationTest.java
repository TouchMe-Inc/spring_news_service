package by.touchme.newsservice.controller;

import by.touchme.newsservice.entity.Comment;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ActiveProfiles(profiles = "test")
@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureRestDocs(outputDir = "target/snippets")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class CommentControllerIntegrationTest {

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private MockMvc mockMvc;

    private final String URL = "/v1/comment";
    private final String DOC_IDENTIFIER = "comment/{methodName}";

    private final Long NOT_FOUND_ID = 0L;
    private final Long CORRECT_ID = 1L;
    private final Long DELETE_ID = 2L;

    @DisplayName("Integration test for CommentController.getPage")
    @Order(1)
    @Test
    public void getPage() throws Exception {
        this.mockMvc.perform(
                        get(URL)
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(status().isOk())
                .andDo(document(DOC_IDENTIFIER));
    }


    @DisplayName("Integration test for CommentController.getById")
    @Order(2)
    @Test
    public void getById() throws Exception {
        this.mockMvc
                .perform(
                        get(URL + "/{id}", CORRECT_ID)
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(status().isOk())
                .andDo(document(DOC_IDENTIFIER));
    }

    @DisplayName("Integration test for CommentController.getById")
    @Order(3)
    @Test
    public void getByIdNotFound() throws Exception {
        this.mockMvc
                .perform(
                        get(URL + "/{id}", NOT_FOUND_ID)
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(status().isNotFound())
                .andDo(document(DOC_IDENTIFIER));
    }

    @DisplayName("Integration test for CommentController.create")
    @Order(4)
    @Test
    public void create() throws Exception {
        Comment newComment = this.getComment();

        this.mockMvc.perform(
                        post(URL)
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(newComment))
                )
                .andDo(print())
                .andExpect(status().isCreated())
                .andDo(document(DOC_IDENTIFIER));
    }

    @DisplayName("Integration test for CommentController.updateById")
    @Order(5)
    @Test
    public void updateById() throws Exception {
        Comment updateComment = this.getComment();

        this.mockMvc.perform(
                        put(URL + "/{id}", CORRECT_ID)
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(updateComment))
                )
                .andDo(print())
                .andExpect(status().isOk())
                .andDo(document(DOC_IDENTIFIER));
    }

    @DisplayName("Integration test for CommentController.updateById")
    @Order(6)
    @Test
    public void updateByIdNotFound() throws Exception {
        Comment updateComment = this.getComment();

        this.mockMvc.perform(
                        put(URL + "/{id}", NOT_FOUND_ID)
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(updateComment))
                )
                .andDo(print())
                .andExpect(status().isNotFound())
                .andDo(document(DOC_IDENTIFIER));
    }

    @DisplayName("Integration test for CommentController.deleteById")
    @Order(7)
    @Test
    public void deleteById() throws Exception {
        this.mockMvc
                .perform(
                        delete(URL + "/{id}", DELETE_ID)
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(status().isOk())
                .andDo(document(DOC_IDENTIFIER));
    }

    @DisplayName("Integration test for CommentController.deleteById")
    @Order(8)
    @Test
    public void deleteByIdNotFound() throws Exception {
        this.mockMvc
                .perform(
                        delete(URL + "/{id}", NOT_FOUND_ID)
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(status().isNotFound())
                .andDo(document(DOC_IDENTIFIER));
    }

    private Comment getComment() {
        Comment comment = new Comment();
        comment.setUsername("John Doe");
        comment.setText("Lorem ipsum dolor sit amet, consectetur adipiscing elit.");

        return comment;
    }
}
