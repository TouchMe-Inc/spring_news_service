package by.touchme.newsservice.service.impl;

import by.touchme.newsservice.entity.News;
import by.touchme.newsservice.exception.CommentNotFoundException;
import by.touchme.newsservice.exception.NewsNotFoundException;
import by.touchme.newsservice.repository.NewsRepository;
import by.touchme.newsservice.service.NewsService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class NewsServiceImpl implements NewsService {
    private NewsRepository newsRepository;

    @Override
    public News getById(Long id) {
        return newsRepository
                .findById(id)
                .orElseThrow(() -> new NewsNotFoundException(id));
    }

    @Override
    public Page<News> getPage(Pageable pageable) {
        return newsRepository.findAll(pageable);
    }

    @Override
    public News create(News news) {
        return newsRepository.save(news);
    }

    @Override
    public News updateById(Long id, News news) {
        if (newsRepository.existsById(id)) {
            throw new CommentNotFoundException(id);
        }

        news.setId(id);

        return newsRepository.save(news);
    }

    @Override
    public void deleteById(Long id) {
        News news = newsRepository
                .findById(id)
                .orElseThrow(() -> new NewsNotFoundException(id));

        newsRepository.deleteById(news.getId());
    }
}