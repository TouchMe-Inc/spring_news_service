package by.touchme.newsservice.controller;

import by.touchme.newsservice.cache.Cache;
import by.touchme.newsservice.entity.Comment;
import by.touchme.newsservice.service.CommentService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/v1/comment")
@RestController
public class CommentController {

    private final CommentService commentService;
    private final Cache<Long, Comment> cache;

    CommentController(CommentService commentService, Cache<Long, Comment> cache) {
        this.commentService = commentService;
        this.cache = cache;
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Comment getById(@PathVariable(name = "id") Long id) {
        Comment comment = this.cache.get(id)
                .orElse(this.commentService.getById(id));

        this.cache.put(id, comment);

        return comment;
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public Page<Comment> getPage(Pageable pageable) {
        return commentService.getPage(pageable);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Comment create(@RequestBody Comment comment) {
        Comment createdComment = this.commentService.create(comment);

        this.cache.put(createdComment.getId(), createdComment);

        return createdComment;
    }

    @PutMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Comment updateById(@PathVariable(name = "id") Long id, @RequestBody Comment comment) {
        Comment updatedComment = this.commentService.updateById(id, comment);

        this.cache.put(id, updatedComment);

        return updatedComment;
    }

    @DeleteMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteById(@PathVariable(name = "id") Long id) {
        commentService.deleteById(id);
        this.cache.remove(id);
    }
}
