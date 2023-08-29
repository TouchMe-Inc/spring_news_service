package by.touchme.newsservice.service.impl;

import by.touchme.newsservice.dto.NewsDto;
import by.touchme.newsservice.entity.News;
import by.touchme.newsservice.exception.NewsNotFoundException;
import by.touchme.newsservice.mapper.NewsMapper;
import by.touchme.newsservice.repository.NewsRepository;
import by.touchme.newsservice.service.NewsService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Slf4j
@Service
public class NewsServiceImpl implements NewsService {
    private final NewsRepository newsRepository;
    private final NewsMapper newsMapper;

    @Override
    public NewsDto getById(Long id) {
        log.info("Get news with id = {}", id);
        return this.newsMapper.modelToDto(
                this.newsRepository
                        .findById(id)
                        .orElseThrow(() -> new NewsNotFoundException(id))
        );
    }

    @Override
    public Page<NewsDto> getPage(Pageable pageable) {
        log.info("Get news page ({})", pageable);
        Page<News> page = this.newsRepository.findAll(pageable);

        return page.map(this.newsMapper::modelToDto);
    }

    @Override
    public NewsDto create(NewsDto news) {
        log.info("Create news ({})", news);
        return this.newsMapper.modelToDto(
                this.newsRepository.save(
                        this.newsMapper.dtoToModel(news)
                )
        );
    }

    @Override
    public NewsDto updateById(Long id, NewsDto news) {
        if (!this.newsRepository.existsById(id)) {
            throw new NewsNotFoundException(id);
        }

        news.setId(id);

        log.info("Update news with id = {} ({})", id, news);
        return this.newsMapper.modelToDto(
                this.newsRepository.save(
                        this.newsMapper.dtoToModel(news)
                )
        );
    }

    @Override
    public void deleteById(Long id) {
        if (!this.newsRepository.existsById(id)) {
            throw new NewsNotFoundException(id);
        }

        log.info("Delete news with id = {}", id);
        this.newsRepository.deleteById(id);
    }
}
