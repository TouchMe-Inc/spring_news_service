package by.touchme.newsservice.service;

import by.touchme.newsservice.entity.News;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface NewsService {
    News getById(Long id);

    Page<News> getPage(Pageable pageable);

    News create(News comment);

    News updateById(Long id, News news);

    void deleteById(Long id);
}
