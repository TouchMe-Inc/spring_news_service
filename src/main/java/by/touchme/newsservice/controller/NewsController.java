package by.touchme.newsservice.controller;

import by.touchme.newsservice.dto.NewsDto;
import by.touchme.newsservice.service.NewsService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;


/**
 * CRUD controller for news.
 */
@RequestMapping("/v1/news")
@RequiredArgsConstructor
@RestController
public class NewsController {

    private final NewsService newsService;

    @Cacheable(cacheNames = "news", key = "#id")
    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public NewsDto getById(@PathVariable(name = "id") Long id) {
        return newsService.getById(id);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public Page<NewsDto> getPage(Pageable pageable) {
        return newsService.getPage(pageable);
    }

    @CachePut(cacheNames = "news", key = "#result.id")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public NewsDto create(@Valid @RequestBody NewsDto news) {
        return newsService.create(news);
    }

    @CachePut(cacheNames = "news", key = "#id")
    @PutMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public NewsDto updateById(@PathVariable(name = "id") Long id, @Valid @RequestBody NewsDto news) {
        return newsService.updateById(id, news);
    }

    @CacheEvict(cacheNames = "news", key = "#id")
    @DeleteMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteById(@PathVariable(name = "id") Long id) {
        newsService.deleteById(id);
    }
}
