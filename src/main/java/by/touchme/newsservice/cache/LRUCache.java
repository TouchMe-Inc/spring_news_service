package by.touchme.newsservice.cache;

import org.springframework.cache.Cache;
import org.springframework.cache.support.SimpleValueWrapper;

import java.util.LinkedList;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class LRUCache implements Cache {
    private final String cacheName;
    private final int capacity;
    private final Map<Object, Object> data;
    private final LinkedList<Object> order;
    private final ReentrantReadWriteLock lock;

    public LRUCache(String cacheName, int capacity) {
        this.cacheName = cacheName;
        this.capacity = capacity;
        this.data = new ConcurrentHashMap<>(capacity);
        this.order = new LinkedList<>();
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

            this.order.remove(key);
            this.order.addFirst(key);

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
            if (this.data.containsKey(key)) {
                this.order.remove(key);
            } else if (this.data.size() >= this.capacity) {
                Object keyRemoved = this.order.removeLast();
                this.evict(keyRemoved);
            }

            this.data.put(key, value);
            this.order.addFirst(key);
        } finally {
            this.lock.writeLock().unlock();
        }
    }

    @Override
    public void evict(Object key) {
        this.data.remove(key);
        this.order.remove(key);
    }

    @Override
    public void clear() {
        this.lock.writeLock().lock();

        try {
            this.data.clear();
            this.order.clear();
        } finally {
            this.lock.writeLock().unlock();
        }
    }
}