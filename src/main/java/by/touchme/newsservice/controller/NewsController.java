package by.touchme.newsservice.controller;

import by.touchme.newsservice.entity.News;
import by.touchme.newsservice.service.NewsService;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;


@RequestMapping("/v1/news")
@RestController
public class NewsController {

    private final NewsService newsService;

    NewsController(NewsService newsService) {
        this.newsService = newsService;
    }

    @Cacheable(cacheNames = "news", key = "#id")
    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public News getById(@PathVariable(name = "id") Long id) {
        return this.newsService.getById(id);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public Page<News> getPage(Pageable pageable) {
        return this.newsService.getPage(pageable);
    }

    @CachePut(cacheNames = "news", key = "#news.id")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public News create(@RequestBody News news) {
        return this.newsService.create(news);
    }

    @CachePut(cacheNames = "news", key = "#id")
    @PutMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public News updateById(@PathVariable(name = "id") Long id, @RequestBody News news) {
        return this.newsService.updateById(id, news);
    }

    @CacheEvict(cacheNames = "news", key = "#id")
    @DeleteMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteById(@PathVariable(name = "id") Long id) {
        this.newsService.deleteById(id);
    }
}
