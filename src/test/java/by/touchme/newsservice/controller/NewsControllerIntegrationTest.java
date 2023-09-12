package by.touchme.newsservice.controller;

import by.touchme.newsservice.dto.NewsDto;
import by.touchme.newsservice.service.PermissionService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
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

    @MockBean
    private PermissionService permissionService;

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

    @DisplayName("Integration test for NewsController.getById with response NotFound")
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
    @WithMockUser(authorities = {"ROLE_ADMIN"})
    @Test
    void create() throws Exception {
        NewsDto createNews = new NewsDto();
        createNews.setAuthor("Admin");
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
    @WithMockUser(authorities = {"ROLE_ADMIN"})
    @Test
    void updateById() throws Exception {
        NewsDto updateNews = new NewsDto();
        updateNews.setAuthor("Admin");
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

    @DisplayName("Integration test for NewsController.updateById with response NotFound")
    @WithMockUser(authorities = {"ROLE_ADMIN"})
    @Test
    void updateByIdNotFound() throws Exception {
        NewsDto updateNews = new NewsDto();
        updateNews.setAuthor("Admin");
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
    @WithMockUser(authorities = {"ROLE_ADMIN"})
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

    @DisplayName("Integration test for NewsController.deleteById with response NotFound")
    @WithMockUser(authorities = {"ROLE_ADMIN"})
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
