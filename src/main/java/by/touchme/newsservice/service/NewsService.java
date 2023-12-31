package by.touchme.newsservice.service;

import by.touchme.newsservice.dto.NewsDto;
import by.touchme.newsservice.dto.PageDto;
import by.touchme.newsservice.dto.SearchDto;
import by.touchme.newsservice.entity.News;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

public interface NewsService {
    NewsDto getById(Long id);

    PageDto<NewsDto> getPageByCriteria(SearchDto search, Pageable pageable);

    PageDto<NewsDto> getPage(Pageable pageable);

    NewsDto create(NewsDto comment);

    NewsDto updateById(Long id, NewsDto news);

    void deleteById(Long id);
}
