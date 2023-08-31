package by.touchme.newsservice.controller;

import by.touchme.newsservice.dto.NewsDto;
import by.touchme.newsservice.dto.SearchDto;
import by.touchme.newsservice.service.NewsService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/v1/news")
@RestController
public class SearchNewsController {

    private final NewsService newsService;

    SearchNewsController(NewsService newsService) {
        this.newsService = newsService;
    }

    @GetMapping("/search")
    @ResponseStatus(HttpStatus.OK)
    public Page<NewsDto> getPage(@Valid @RequestBody SearchDto searchDto, Pageable pageable) {
        return this.newsService.getPageByCriteria(searchDto, pageable);
    }
}
