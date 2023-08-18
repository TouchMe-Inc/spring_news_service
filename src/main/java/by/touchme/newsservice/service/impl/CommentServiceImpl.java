package by.touchme.newsservice.service.impl;

import by.touchme.newsservice.entity.Comment;
import by.touchme.newsservice.exception.CommentNotFoundException;
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

    @Override
    public Comment getById(Long id) {
        return commentRepository
                .findById(id)
                .orElseThrow(() -> new CommentNotFoundException(id));
    }

    @Override
    public Page<Comment> getPage(Pageable pageable) {
        return commentRepository.findAll(pageable);
    }

    @Override
    public Comment create(Comment comment) {
        return commentRepository.save(comment);
    }

    @Override
    public Comment updateById(Long id, Comment comment) {
        if (commentRepository.existsById(id)) {
            throw new CommentNotFoundException(id);
        }

        comment.setId(id);

        return commentRepository.save(comment);
    }

    @Override
    public void deleteById(Long id) {
        Comment comment = commentRepository
                .findById(id)
                .orElseThrow(() -> new CommentNotFoundException(id));

        commentRepository.deleteById(comment.getId());
    }
}
