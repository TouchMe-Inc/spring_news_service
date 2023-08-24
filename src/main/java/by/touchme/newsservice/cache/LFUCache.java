package by.touchme.newsservice.cache;

import org.springframework.cache.Cache;
import org.springframework.cache.support.SimpleValueWrapper;

import java.util.*;
import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class LFUCache implements Cache {
    private final String cacheName;
    private final int capacity;
    private final Map<Object, Object> data;
    private final Map<Object, Integer> frequency;
    private final ReentrantReadWriteLock lock;

    public LFUCache(String cacheName, int capacity) {
        this.cacheName = cacheName;
        this.capacity = capacity;
        this.data = new ConcurrentHashMap<>(capacity);
        this.frequency = new ConcurrentHashMap<>(capacity);
        this.lock = new ReentrantReadWriteLock();
    }

    @Override
    public String getName() {
        return this.cacheName;
    }

    @Override
    public Object getNativeCache() {
        return null;
    }

    @Override
    public ValueWrapper get(Object key) {
        this.lock.readLock().lock();

        try {
            if (!this.data.containsKey(key)) {
                return null;
            }

            this.frequency.put(key, this.frequency.get(key) + 1);

            return new SimpleValueWrapper(this.data.get(key));
        } finally {
            this.lock.readLock().unlock();
        }
    }

    @Override
    public <T> T get(Object key, Class<T> type) {
        return null;
    }

    @Override
    public <T> T get(Object key, Callable<T> valueLoader) {
        return null;
    }

    @Override
    public void put(Object key, Object value) {
        this.lock.writeLock().lock();

        try {
            int frequency = 1;

            if (this.data.containsKey(key)) {
                frequency += this.frequency.get(key);
            } else if (this.data.size() >= this.capacity) {
                // Add Tree for O(1) and order for remove only old record
                Map.Entry<Object, Integer> removedEntry = Collections.min(
                        this.frequency.entrySet(),
                        Map.Entry.comparingByValue()
                );
                this.evict(removedEntry.getKey());
            }

            this.data.put(key, value);
            this.frequency.put(key, frequency);
        } finally {
            this.lock.writeLock().unlock();
        }
    }

    @Override
    public void evict(Object key) {
        this.data.remove(key);
        this.frequency.remove(key);
    }

    @Override
    public void clear() {
        this.lock.writeLock().lock();

        try {
            this.data.clear();
            this.frequency.clear();
        } finally {
            this.lock.writeLock().unlock();
        }
    }
}