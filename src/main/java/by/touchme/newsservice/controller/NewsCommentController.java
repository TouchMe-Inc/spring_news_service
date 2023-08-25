package by.touchme.newsservice.controller;

import by.touchme.newsservice.dto.CommentDto;
import by.touchme.newsservice.service.CommentService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/v1/news")
@RestController
public class NewsCommentController {

    private final CommentService commentService;

    NewsCommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @GetMapping("/{id}/comment")
    @ResponseStatus(HttpStatus.OK)
    public Page<CommentDto> getPage(@PathVariable(name = "id") Long newsId, Pageable pageable) {
        return this.commentService.getPageByNewsId(newsId, pageable);
    }
}
