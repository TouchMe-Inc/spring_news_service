package by.touchme.newsservice.mapper;

import by.touchme.newsservice.dto.CommentDto;
import by.touchme.newsservice.dto.NewsDto;
import by.touchme.newsservice.entity.Comment;
import by.touchme.newsservice.entity.News;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CommentMapper {

    Comment dtoToModel(CommentDto commentDto);

    CommentDto modelToDto(Comment comment);

    List<CommentDto> toListDto(List<Comment> commentList);
}
