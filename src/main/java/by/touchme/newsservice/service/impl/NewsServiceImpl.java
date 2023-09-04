package by.touchme.newsservice.service.impl;

import by.touchme.newsservice.criteria.SearchCriteria;
import by.touchme.newsservice.dto.CommentDto;
import by.touchme.newsservice.dto.NewsDto;
import by.touchme.newsservice.dto.PageDto;
import by.touchme.newsservice.dto.SearchDto;
import by.touchme.newsservice.entity.News;
import by.touchme.newsservice.exception.NewsNotFoundException;
import by.touchme.newsservice.mapper.NewsMapper;
import by.touchme.newsservice.repository.NewsRepository;
import by.touchme.newsservice.service.NewsService;
import by.touchme.newsservice.specification.NewsSpecification;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Slf4j
@Service
public class NewsServiceImpl implements NewsService {
    private final NewsRepository newsRepository;
    private final NewsMapper newsMapper;

    @Override
    public NewsDto getById(Long id) {
        log.info("Get news with id = {}", id);
        return newsMapper.modelToDto(
                newsRepository
                        .findById(id)
                        .orElseThrow(() -> new NewsNotFoundException(id))
        );
    }

    @Override
    public PageDto<NewsDto> getPageByCriteria(SearchDto search, Pageable pageable) {
        log.info("Get news page ({}) by criteria {}", pageable, search);

        List<SearchCriteria> criteriaList = search.getCriteriaList();

        Specification<News> specification = null;

        if (!criteriaList.isEmpty()) {
            specification = new NewsSpecification(criteriaList.get(0));
            for (int idx = 1; idx < criteriaList.size(); idx++) {
                SearchCriteria criteria = criteriaList.get(idx);
                specification = Specification.where(specification).and(new NewsSpecification(criteria));
            }
        }

        Page<News> page = newsRepository.findAll(specification, pageable);

        return new PageDto<>(page.map(newsMapper::modelToDto));
    }

    @Override
    public PageDto<NewsDto> getPage(Pageable pageable) {
        log.info("Get news page ({})", pageable);
        Page<News> page = newsRepository.findAll(pageable);

        return new PageDto<>(page.map(newsMapper::modelToDto));
    }

    @Override
    public NewsDto create(NewsDto news) {
        log.info("Create news ({})", news);
        return newsMapper.modelToDto(
                newsRepository.save(
                        newsMapper.dtoToModel(news)
                )
        );
    }

    @Override
    public NewsDto updateById(Long id, NewsDto news) {
        if (!newsRepository.existsById(id)) {
            throw new NewsNotFoundException(id);
        }

        news.setId(id);

        log.info("Update news with id = {} ({})", id, news);
        return newsMapper.modelToDto(
                newsRepository.save(
                        newsMapper.dtoToModel(news)
                )
        );
    }

    @Override
    public void deleteById(Long id) {
        if (!newsRepository.existsById(id)) {
            throw new NewsNotFoundException(id);
        }

        log.info("Delete news with id = {}", id);
        newsRepository.deleteById(id);
    }
}
