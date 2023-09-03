package by.touchme.newsservice.controller;

import by.touchme.newsservice.dto.NewsDto;
import by.touchme.newsservice.dto.SearchDto;
import by.touchme.newsservice.service.NewsService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/v1/news")
@RequiredArgsConstructor
@RestController
public class SearchNewsController {

    private final NewsService newsService;

    @GetMapping("/search")
    @ResponseStatus(HttpStatus.OK)
    public Page<NewsDto> getPage(@Valid @RequestBody SearchDto searchDto, Pageable pageable) {
        return newsService.getPageByCriteria(searchDto, pageable);
    }
}
