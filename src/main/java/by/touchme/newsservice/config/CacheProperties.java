package by.touchme.newsservice.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

@ConfigurationProperties(prefix = "cache")
@ConfigurationPropertiesScan
public record CacheProperties(String type, int capacity) {
}
