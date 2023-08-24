package by.touchme.newsservice.config;


import by.touchme.newsservice.cache.LFUCache;
import by.touchme.newsservice.cache.LRUCache;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.support.SimpleCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;


@EnableConfigurationProperties(CacheProperties.class)
@EnableCaching
@Configuration
public class CacheConfiguration {

    private final CacheProperties properties;

    CacheConfiguration(CacheProperties properties) {
        this.properties = properties;
    }

    @Bean
    public CacheManager cacheManager() {
        SimpleCacheManager cacheManager = new SimpleCacheManager();

        cacheManager.setCaches(
                Arrays.asList(
                        createCache("news"),
                        createCache("comments")
                )
        );

        return cacheManager;
    }

    private Cache createCache(String name) {
        int capacity = this.properties.getCapacity();

        return switch (this.properties.getType()) {
            case LRU -> new LRUCache(name, capacity);
            case LFU -> new LFUCache(name, capacity);
        };
    }
}
