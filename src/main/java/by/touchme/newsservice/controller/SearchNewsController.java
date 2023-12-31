package by.touchme.newsservice.controller;

import by.touchme.newsservice.dto.NewsDto;
import by.touchme.newsservice.dto.PageDto;
import by.touchme.newsservice.dto.SearchDto;
import by.touchme.newsservice.service.NewsService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/v1/news")
@RequiredArgsConstructor
@RestController
public class SearchNewsController {

    private final NewsService newsService;

    /**
     * Endpoint for retrieving news using a paginated search expression.
     *
     * @param searchDto Search criteria
     * @param pageable Pagination options
     * @return PageDto with NewsDto
     */
    @GetMapping("/search")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<PageDto<NewsDto>> search(@Valid @RequestBody SearchDto searchDto, Pageable pageable) {
        return new ResponseEntity<>(newsService.getPageByCriteria(searchDto, pageable), HttpStatus.OK);
    }
}
