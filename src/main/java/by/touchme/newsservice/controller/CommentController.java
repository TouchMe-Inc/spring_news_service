package by.touchme.newsservice.controller;

import by.touchme.newsservice.dto.CommentDto;
import by.touchme.newsservice.service.CommentService;
import jakarta.validation.Valid;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

/**
 * CRUD controller for comments.
 */
@RequestMapping("/v1/comment")
@RestController
public class CommentController {

    private final CommentService commentService;

    CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @Cacheable(cacheNames = "comments", key = "#id")
    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public CommentDto getById(@PathVariable(name = "id") Long id) {
        return this.commentService.getById(id);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public Page<CommentDto> getPage(Pageable pageable) {
        return commentService.getPage(pageable);
    }

    @CachePut(cacheNames = "comments", key = "#result.id")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CommentDto create(@Valid @RequestBody CommentDto comment) {
        return this.commentService.create(comment);
    }

    @CachePut(cacheNames = "comments", key = "#id")
    @PutMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public CommentDto updateById(@PathVariable(name = "id") Long id, @Valid @RequestBody CommentDto comment) {
        return this.commentService.updateById(id, comment);
    }

    @CacheEvict(cacheNames = "comments", key = "#id")
    @DeleteMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteById(@PathVariable(name = "id") Long id) {
        this.commentService.deleteById(id);
    }
}
