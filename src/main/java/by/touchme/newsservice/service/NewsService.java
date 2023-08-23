package by.touchme.newsservice.service;

import by.touchme.newsservice.dto.NewsDto;
import by.touchme.newsservice.entity.News;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface NewsService {
    NewsDto getById(Long id);

    Page<NewsDto> getPage(Pageable pageable);

    NewsDto create(NewsDto comment);

    NewsDto updateById(Long id, NewsDto news);

    void deleteById(Long id);
}
