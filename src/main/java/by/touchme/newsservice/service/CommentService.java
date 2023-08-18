package by.touchme.newsservice.service;

import by.touchme.newsservice.entity.Comment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CommentService {
    Comment getById(Long id);

    Page<Comment> getPage(Pageable pageable);

    Comment create(Comment comment);

    Comment updateById(Long id, Comment comment);

    void deleteById(Long id);
}
