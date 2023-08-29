package by.touchme.newsservice.service.impl;

import by.touchme.newsservice.dto.CommentDto;
import by.touchme.newsservice.entity.Comment;
import by.touchme.newsservice.exception.CommentNotFoundException;
import by.touchme.newsservice.mapper.CommentMapper;
import by.touchme.newsservice.repository.CommentRepository;
import by.touchme.newsservice.service.CommentService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Slf4j
@Service
public class CommentServiceImpl implements CommentService {
    private CommentRepository commentRepository;
    private final CommentMapper commentMapper;

    @Override
    public CommentDto getById(Long id) {
        log.info("Get comment with id = {}", id);
        return this.commentMapper.modelToDto(
                this.commentRepository
                        .findById(id)
                        .orElseThrow(() -> new CommentNotFoundException(id))
        );
    }

    @Override
    public Page<CommentDto> getPage(Pageable pageable) {
        log.info("Get comment page ({})", pageable);
        Page<Comment> page = this.commentRepository.findAll(pageable);

        return page.map(this.commentMapper::modelToDto);
    }

    @Override
    public Page<CommentDto> getPageByNewsId(Long newsId, Pageable pageable) {
        log.info("Get comment page ({}) with news_id = {}", pageable, newsId);
        Page<Comment> page = this.commentRepository.findAllByNewsId(newsId, pageable);

        return page.map(this.commentMapper::modelToDto);
    }

    @Override
    public CommentDto create(CommentDto comment) {
        log.info("Create comment ({})", comment);
        return this.commentMapper.modelToDto(
                this.commentRepository.save(
                        this.commentMapper.dtoToModel(comment)
                )
        );
    }

    @Override
    public CommentDto updateById(Long id, CommentDto comment) {
        if (!this.commentRepository.existsById(id)) {
            throw new CommentNotFoundException(id);
        }

        comment.setId(id);

        log.info("Update comment with id = {} ({})", id, comment);
        return this.commentMapper.modelToDto(
                this.commentRepository.save(
                        this.commentMapper.dtoToModel(comment)
                )
        );
    }

    @Override
    public void deleteById(Long id) {
        if (!this.commentRepository.existsById(id)) {
            throw new CommentNotFoundException(id);
        }

        log.info("Delete comment with id = {}", id);
        this.commentRepository.deleteById(id);
    }
}
