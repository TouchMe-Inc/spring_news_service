package by.touchme.newsservice.config;

import lombok.Getter;

@Getter
public enum CacheTypes {

    NONE,
    LFU,
    LRU,
    REDIS,
}
