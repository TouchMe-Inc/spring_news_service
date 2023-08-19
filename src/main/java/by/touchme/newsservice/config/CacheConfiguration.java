package by.touchme.newsservice.config;


import by.touchme.newsservice.cache.Cache;
import by.touchme.newsservice.cache.impl.LFUCache;
import by.touchme.newsservice.cache.impl.LRUCache;
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
    public Cache<?, ?> cache() {
        return factoryCache(this.properties.getType(), this.properties.getCapacity());
    }

    private Cache<?, ?> factoryCache(CacheTypes type, int capacity) {
        return switch (type) {
            case LRU -> new LRUCache<>(capacity);
            case LFU -> new LFUCache<>(capacity);
        };
    }
}
