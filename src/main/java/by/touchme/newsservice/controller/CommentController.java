package by.touchme.newsservice.controller;

import by.touchme.newsservice.entity.Comment;
import by.touchme.newsservice.exception.CommentNotFoundException;
import by.touchme.newsservice.service.CommentService;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

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
    public Comment getById(@PathVariable(name = "id") Long id) {
        return this.commentService.getById(id);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public Page<Comment> getPage(Pageable pageable) {
        return commentService.getPage(pageable);
    }

    @CachePut(cacheNames = "comments", key = "#comment.id")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Comment create(@RequestBody Comment comment) {
        return this.commentService.create(comment);
    }

    @CachePut(cacheNames = "comments", key = "#id")
    @PutMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public Comment updateById(@PathVariable(name = "id") Long id, @RequestBody Comment comment) {
        return this.commentService.updateById(id, comment);
    }

    @CacheEvict(cacheNames = "comments", key = "#id")
    @DeleteMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteById(@PathVariable(name = "id") Long id) {
        this.commentService.deleteById(id);
    }
}
