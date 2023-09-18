package by.touchme.newsservice.controller;

import by.touchme.newsservice.criteria.SearchCriteria;
import by.touchme.newsservice.criteria.SearchOperation;
import by.touchme.newsservice.dto.SearchDto;
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
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ActiveProfiles(profiles = "test")
@AutoConfigureMockMvc(addFilters = false)
@WebMvcTest(SearchNewsController.class)
public class SearchNewsControllerUnitTest {


    final String URL = "/v1/news/search";

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

    @DisplayName("JUnit test for SearchNewsController.search without body")
    @WithMockUser
    @Test
    void searchWithoutBody() throws Exception {
        mockMvc
                .perform(
                        get(URL)
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @DisplayName("JUnit test for SearchNewsController.search without criteria list")
    @WithMockUser
    @Test
    void searchWithoutCriteriaList() throws Exception {
        SearchDto searchDto = new SearchDto();

        mockMvc
                .perform(
                        get(URL)
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(searchDto))
                )
                .andDo(print())
                .andExpect(status().isBadRequest());
    }


    @DisplayName("JUnit test for SearchNewsController.search")
    @WithMockUser
    @Test
    void search() throws Exception {
        SearchCriteria searchCriteria = new SearchCriteria();
        searchCriteria.setKey("title");
        searchCriteria.setOperation(SearchOperation.CONTAINS);
        searchCriteria.setValue("Lorem");

        SearchDto searchDto = new SearchDto();
        searchDto.setCriteriaList(List.of(searchCriteria));

        mockMvc
                .perform(
                        get(URL)
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(searchDto))
                )
                .andDo(print())
                .andExpect(status().isOk());
    }
}
