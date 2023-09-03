package by.touchme.newsservice.service;

import by.touchme.newsservice.dto.CommentDto;
import by.touchme.newsservice.dto.PageDto;
import by.touchme.newsservice.entity.Comment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CommentService {
    CommentDto getById(Long id);

    PageDto<CommentDto> getPage(Pageable pageable);

    PageDto<CommentDto> getPageByNewsId(Long newsId, Pageable pageable);

    CommentDto create(CommentDto comment);

    CommentDto updateById(Long id, CommentDto comment);

    void deleteById(Long id);
}
