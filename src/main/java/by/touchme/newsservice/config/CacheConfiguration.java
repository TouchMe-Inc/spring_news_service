package by.touchme.newsservice.config;


import by.touchme.newsservice.cache.Cache;
import by.touchme.newsservice.cache.impl.LFUCache;
import by.touchme.newsservice.cache.impl.LRUCache;
import by.touchme.newsservice.cache.impl.NoCache;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@EnableConfigurationProperties(CacheProperties.class)
@Configuration
public class CacheConfiguration {

    private final CacheProperties properties;

    private final String TYPE_LRU ="lru";
    private final String TYPE_LFU ="lfu";

    CacheConfiguration(CacheProperties properties) {
        this.properties = properties;
    }

    @Bean
    public Cache<?, ?> cache() {
        return factoryCache(this.properties.type(), this.properties.capacity());
    }

    private Cache<?, ?> factoryCache(String type, int capacity) {
        return switch (type) {
            case TYPE_LRU -> new LRUCache<>(capacity);
            case TYPE_LFU -> new LFUCache<>(capacity);
            default -> new NoCache<>(capacity);
        };
    }
}
