package by.touchme.newsservice.controller;

import by.touchme.newsservice.entity.Comment;
import by.touchme.newsservice.service.CommentService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@AllArgsConstructor
@RequestMapping("/v1/comment")
@RestController
public class CommentController {

    private CommentService commentService;

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Comment getById(@PathVariable(name = "id") Long id) {
        return commentService.getById(id);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public Page<Comment> getPage(Pageable pageable) {
        return commentService.getPage(pageable);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Comment create(@RequestBody Comment comment) {
        return commentService.create(comment);
    }

    @PutMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Comment updateById(@PathVariable(name = "id") Long id, @RequestBody Comment comment) {
        return commentService.updateById(id, comment);
    }

    @DeleteMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteById(@PathVariable(name = "id") Long id) {
        commentService.deleteById(id);
    }
}
