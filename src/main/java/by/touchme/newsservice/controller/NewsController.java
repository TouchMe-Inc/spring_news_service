package by.touchme.newsservice.controller;

import by.touchme.newsservice.cache.Cache;
import by.touchme.newsservice.cache.impl.LRUCache;
import by.touchme.newsservice.entity.News;
import by.touchme.newsservice.service.NewsService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;


@RequestMapping("/v1/news")
@RestController
public class NewsController {

    private final NewsService newsService;
    private final Cache<Long, News> cache;

    NewsController(NewsService newsService) {
        this.newsService = newsService;
        this.cache = new LRUCache<>(10);
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public News getById(@PathVariable(name = "id") Long id) {
        News news = this.cache.get(id)
                .orElse(this.newsService.getById(id));

        this.cache.set(id, news);

        return news;
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public Page<News> getPage(Pageable pageable) {
        return this.newsService.getPage(pageable);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public News create(@RequestBody News news) {
        News createdNews = this.newsService.create(news);

        this.cache.set(createdNews.getId(), createdNews);

        return createdNews;
    }

    @PutMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.OK)
    public News updateById(@PathVariable(name = "id") Long id, @RequestBody News news) {
        News updatedNews = this.newsService.updateById(id, news);

        this.cache.set(id, updatedNews);

        return updatedNews;
    }

    @DeleteMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteById(@PathVariable(name = "id") Long id) {
        this.newsService.deleteById(id);
        this.cache.set(id, null);
    }
}
