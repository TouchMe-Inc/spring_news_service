package by.touchme.newsservice.mapper;

import by.touchme.newsservice.dto.NewsDto;
import by.touchme.newsservice.entity.News;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface NewsMapper {

    News dtoToModel(NewsDto newsDto);

    NewsDto modelToDto(News book);

    List<NewsDto> toListDto(List<News> newsList);
}
