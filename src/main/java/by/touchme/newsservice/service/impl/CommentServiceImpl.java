package by.touchme.newsservice.service.impl;

import by.touchme.newsservice.dto.CommentDto;
import by.touchme.newsservice.entity.Comment;
import by.touchme.newsservice.exception.CommentNotFoundException;
import by.touchme.newsservice.mapper.CommentMapper;
import by.touchme.newsservice.repository.CommentRepository;
import by.touchme.newsservice.service.CommentService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class CommentServiceImpl implements CommentService {
    private CommentRepository commentRepository;
    private final CommentMapper commentMapper;

    @Override
    public CommentDto getById(Long id) {
        return this.commentMapper.modelToDto(
                this.commentRepository
                        .findById(id)
                        .orElseThrow(() -> new CommentNotFoundException(id))
        );
    }

    @Override
    public Page<CommentDto> getPage(Pageable pageable) {
        Page<Comment> page = this.commentRepository.findAll(pageable);

        return page.map(this.commentMapper::modelToDto);
    }

    @Override
    public Page<CommentDto> getPageByNewsId(Long newsId, Pageable pageable) {
        Page<Comment> page = this.commentRepository.findAllByNewsId(newsId, pageable);

        return page.map(this.commentMapper::modelToDto);
    }

    @Override
    public CommentDto create(CommentDto comment) {

        Comment comment1 = this.commentRepository.save(
                this.commentMapper.dtoToModel(comment)
        );

        System.out.println(comment1);

        CommentDto dto = this.commentMapper.modelToDto(
                comment1
        );

        System.out.println(dto);

        return dto;
    }

    @Override
    public CommentDto updateById(Long id, CommentDto comment) {
        if (!this.commentRepository.existsById(id)) {
            throw new CommentNotFoundException(id);
        }

        comment.setId(id);

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

        this.commentRepository.deleteById(id);
    }
}
