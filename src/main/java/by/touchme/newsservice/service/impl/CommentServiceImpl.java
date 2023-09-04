package by.touchme.newsservice.service.impl;

import by.touchme.newsservice.dto.CommentDto;
import by.touchme.newsservice.dto.PageDto;
import by.touchme.newsservice.entity.Comment;
import by.touchme.newsservice.exception.CommentNotFoundException;
import by.touchme.newsservice.mapper.CommentMapper;
import by.touchme.newsservice.repository.CommentRepository;
import by.touchme.newsservice.service.CommentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Slf4j
@Service
public class CommentServiceImpl implements CommentService {
    private final CommentRepository commentRepository;
    private final CommentMapper commentMapper;

    @Override
    public CommentDto getById(Long id) {
        log.info("Get comment with id = {}", id);
        return commentMapper.modelToDto(
                commentRepository
                        .findById(id)
                        .orElseThrow(() -> new CommentNotFoundException(id))
        );
    }

    @Override
    public PageDto<CommentDto> getPage(Pageable pageable) {
        log.info("Get comment page ({})", pageable);
        Page<Comment> page = commentRepository.findAll(pageable);

        return new PageDto<>(page.map(commentMapper::modelToDto));
    }

    @Override
    public PageDto<CommentDto> getPageByNewsId(Long newsId, Pageable pageable) {
        log.info("Get comment page ({}) with news_id = {}", pageable, newsId);
        Page<Comment> page = commentRepository.findAllByNewsId(newsId, pageable);

        return new PageDto<>(page.map(commentMapper::modelToDto));
    }

    @Override
    public CommentDto create(CommentDto comment) {
        log.info("Create comment ({})", comment);
        return commentMapper.modelToDto(
                commentRepository.save(
                        commentMapper.dtoToModel(comment)
                )
        );
    }

    @Override
    public CommentDto updateById(Long id, CommentDto comment) {
        if (!commentRepository.existsById(id)) {
            throw new CommentNotFoundException(id);
        }

        comment.setId(id);

        log.info("Update comment with id = {} ({})", id, comment);
        return commentMapper.modelToDto(
                commentRepository.save(
                        commentMapper.dtoToModel(comment)
                )
        );
    }

    @Override
    public void deleteById(Long id) {
        if (!commentRepository.existsById(id)) {
            throw new CommentNotFoundException(id);
        }

        log.info("Delete comment with id = {}", id);
        commentRepository.deleteById(id);
    }
}
