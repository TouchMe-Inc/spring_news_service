package by.touchme.newsservice.controller;

import by.touchme.newsservice.entity.News;
import by.touchme.newsservice.service.NewsService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@AllArgsConstructor
@RequestMapping("/v1/news")
@RestController
public class NewsController {

    private NewsService newsService;

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public News getById(@PathVariable(name = "id") Long id) {
        return newsService.getById(id);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public Page<News> getPage(Pageable pageable) {
        return newsService.getPage(pageable);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public News create(@RequestBody News news) {
        return newsService.create(news);
    }

    @PutMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.OK)
    public News updateById(@PathVariable(name = "id") Long id, @RequestBody News news) {
        return newsService.updateById(id, news);
    }

    @DeleteMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteById(@PathVariable(name = "id") Long id) {
        newsService.deleteById(id);
    }
}
