package by.touchme.newsservice.config;


import by.touchme.newsservice.cache.Cache;
import by.touchme.newsservice.cache.impl.LFUCache;
import by.touchme.newsservice.cache.impl.LRUCache;
import by.touchme.newsservice.entity.Comment;
import by.touchme.newsservice.entity.News;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@EnableConfigurationProperties(CacheProperties.class)
@Configuration
public class CacheConfiguration {

    private final CacheProperties properties;

    CacheConfiguration(CacheProperties properties) {
        this.properties = properties;
    }

    @Bean
    public Cache<Long, News> newsCache() {
        return this.createCache();
    }

    @Bean
    public Cache<Long, Comment> commentCache() {
        return this.createCache();
    }

    private <K, V> Cache<K, V> createCache() {
        return switch (this.properties.getType()) {
            case LRU -> new LRUCache<>(this.properties.getCapacity());
            case LFU -> new LFUCache<>(this.properties.getCapacity());
        };
    }
}
