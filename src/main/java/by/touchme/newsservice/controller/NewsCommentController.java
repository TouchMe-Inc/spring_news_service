package by.touchme.newsservice.controller;

import by.touchme.newsservice.dto.CommentDto;
import by.touchme.newsservice.dto.PageDto;
import by.touchme.newsservice.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Controller for receiving news comments.
 */
@RequestMapping("/v1/news")
@RequiredArgsConstructor
@RestController
public class NewsCommentController {

    private final CommentService commentService;

    @GetMapping("/{id}/comment")
    public ResponseEntity<PageDto<CommentDto>> getPage(
            @PathVariable(name = "id") Long newsId, Pageable pageable) {
        return new ResponseEntity<>(commentService.getPageByNewsId(newsId, pageable), HttpStatus.OK);
    }
}
