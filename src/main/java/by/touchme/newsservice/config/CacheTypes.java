package by.touchme.newsservice.config;

import lombok.Getter;

@Getter
public enum CacheTypes {
    LFU("lfu"),
    LRU("lru");

    private final String type;

    CacheTypes(String type) {
        this.type = type;
    }
}
