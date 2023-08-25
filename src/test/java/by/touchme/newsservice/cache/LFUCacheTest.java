package by.touchme.newsservice.cache;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.cache.Cache;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ActiveProfiles(profiles = "test")
@ExtendWith(SpringExtension.class)
public class LFUCacheTest {

    @DisplayName("JUnit test for LFUCache")
    @Test
    public void getCacheName() {
        Cache cache = new LFUCache("test", 3);
        Assertions.assertEquals("test", cache.getName());
    }

    @DisplayName("JUnit test for LFUCache")
    @Test
    public void getNonExistingCache() {
        Cache cache = new LFUCache("test", 3);
        Assertions.assertNull(cache.get("1"));
    }

    @DisplayName("JUnit test for LFUCache")
    @Test
    public void getExistingCache() {
        Cache cache = new LFUCache("test", 3);
        cache.put("key", "value");

        assertEqualsValueWrapper(cache.get("key"), "value");
    }

    @DisplayName("JUnit test for LFUCache")
    @Test
    public void updateCacheValue() {
        Cache cache = new LFUCache("test", 3);
        cache.put("key", "value");

        // Assert added key
        assertEqualsValueWrapper(cache.get("key"), "value");

        // Update value
        cache.put("key", "newValue");

        // Assert new key value
        assertEqualsValueWrapper(cache.get("key"), "newValue");
    }

    @DisplayName("JUnit test for LFUCache")
    @Test
    public void deleteNonRivalCache() {
        Cache cache = new LFUCache("test", 3);
        cache.put("key1", "value1");
        cache.put("key2", "value2");
        cache.put("key3", "value3");

        // Assert added keys
        assertEqualsValueWrapper(cache.get("key1"), "value1");
        assertEqualsValueWrapper(cache.get("key2"), "value2");
        assertEqualsValueWrapper(cache.get("key3"), "value3");

        // Visit +1 for key1, key3
        cache.get("key1");
        cache.get("key3");

        // Add new key (and remove key2)
        cache.put("newKey", "newValue");

        // Assert result
        assertEqualsValueWrapper(cache.get("newKey"), "newValue");
        assertEqualsValueWrapper(cache.get("key1"), "value1");
        assertEqualsValueWrapper(cache.get("key3"), "value3");

        Assertions.assertNull(cache.get("key2"));
    }

    @DisplayName("JUnit test for LFUCache")
    @Test
    public void deleteCacheByKey() {
        Cache cache = new LFUCache("test", 3);
        cache.put("key", "value");

        // Assert added key
        assertEqualsValueWrapper(cache.get("key"), "value");

        // Remove key
        cache.evict("key");

        // Assert result
        Assertions.assertNull(cache.get("key"));
    }

    @DisplayName("JUnit test for LFUCache")
    @Test
    public void deleteAllCache() {
        Cache cache = new LFUCache("test", 3);
        cache.put("key1", "value1");
        cache.put("key2", "value2");

        // Assert added keys
        assertEqualsValueWrapper(cache.get("key1"), "value1");
        assertEqualsValueWrapper(cache.get("key2"), "value2");

        // Remove key
        cache.clear();

        // Assert result
        Assertions.assertNull(cache.get("key1"));
        Assertions.assertNull(cache.get("key2"));
    }

    void assertEqualsValueWrapper(Cache.ValueWrapper vw, Object value) {
        Assertions.assertNotNull(vw);
        Assertions.assertEquals(vw.get(), value);
    }
}
