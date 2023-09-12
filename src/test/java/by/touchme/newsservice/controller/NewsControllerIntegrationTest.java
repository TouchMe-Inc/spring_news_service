package by.touchme.newsservice.controller;

import by.touchme.newsservice.dto.NewsDto;
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
@AutoConfigureRestDocs
public class NewsControllerIntegrationTest {

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private MockMvc mockMvc;

    private final String URL = "/v1/news";
    private final String DOC_IDENTIFIER = "news/{methodName}";

    private final Long NOT_FOUND_ID = 0L;
    private final Long CORRECT_ID = 1L;
    private final Long DELETE_ID = 2L;

    @DisplayName("Integration test for NewsController.getPage")
    @Test
    void getPage() throws Exception {
        mockMvc.perform(
                        get(URL)
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(status().isOk())
                .andDo(document(DOC_IDENTIFIER));
    }


    @DisplayName("Integration test for NewsController.getById")
    @Test
    void getById() throws Exception {
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

    @DisplayName("Integration test for NewsController.getById")
    @Test
    void getByIdNotFound() throws Exception {
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

    @DisplayName("Integration test for NewsController.create")
    @Test
    void create() throws Exception {
        NewsDto createNews = new NewsDto();
        createNews.setTitle("Lorem Ipsum");
        createNews.setText("Lorem ipsum dolor sit amet, consectetur adipiscing elit.");

        mockMvc.perform(
                        post(URL)
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(createNews))
                )
                .andDo(print())
                .andExpect(status().isCreated())
                .andDo(document(DOC_IDENTIFIER));
    }

    @DisplayName("Integration test for NewsController.updateById")
    @Test
    void updateById() throws Exception {
        NewsDto updateNews = new NewsDto();
        updateNews.setTitle("Lorem Ipsum");
        updateNews.setText("Lorem ipsum dolor sit amet, consectetur adipiscing elit.");

        mockMvc.perform(
                        put(URL + "/{id}", CORRECT_ID)
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(updateNews))
                )
                .andDo(print())
                .andExpect(status().isNoContent())
                .andDo(document(DOC_IDENTIFIER));
    }

    @DisplayName("Integration test for NewsController.updateById")
    @Test
    void updateByIdNotFound() throws Exception {
        NewsDto updateNews = new NewsDto();
        updateNews.setTitle("Lorem Ipsum");
        updateNews.setText("Lorem ipsum dolor sit amet, consectetur adipiscing elit.");

        mockMvc.perform(
                        put(URL + "/{id}", NOT_FOUND_ID)
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(updateNews))
                )
                .andDo(print())
                .andExpect(status().isNotFound())
                .andDo(document(DOC_IDENTIFIER));
    }

    @DisplayName("Integration test for NewsController.deleteById")
    @Test
    void deleteById() throws Exception {
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

    @DisplayName("Integration test for NewsController.deleteById")
    @Test
    void deleteByIdNotFound() throws Exception {
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
}
