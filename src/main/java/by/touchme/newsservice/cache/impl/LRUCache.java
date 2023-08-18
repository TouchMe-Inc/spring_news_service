package by.touchme.newsservice.cache.impl;

import by.touchme.newsservice.cache.Cache;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class LRUCache<K, V> implements Cache<K, V> {
    private final int capacity;

    private final Map<K, V> data;

    private final LinkedList<K> order;

    private final ReentrantReadWriteLock lock;

    public LRUCache(int capacity) {
        this.capacity = capacity;
        this.data = new ConcurrentHashMap<>(capacity);
        this.order = new LinkedList<>();
        this.lock = new ReentrantReadWriteLock();
    }

    @Override
    public void set(K key, V value) {
        this.lock.writeLock().lock();

        try {
            if (this.data.containsKey(key)) {
                this.removeKey(key);
            } else if (this.size() >= this.capacity) {
                K keyRemoved = this.order.removeLast();
                this.removeKey(keyRemoved);
            }

            if (value != null) {
                this.data.put(key, value);
                this.order.addFirst(key);
            }
        } finally {
            this.lock.writeLock().unlock();
        }
    }

    @Override
    public Optional<V> get(K key) {
        this.lock.readLock().lock();

        try {
            V value = this.data.get(key);

            if (value != null) {
                this.order.remove(key);
                this.order.addFirst(key);

                return Optional.of(value);
            }

            return Optional.empty();
        } finally {
            this.lock.readLock().unlock();
        }
    }

    @Override
    public int size() {
        this.lock.readLock().lock();
        try {
            return this.order.size();
        } finally {
            this.lock.readLock().unlock();
        }
    }

    @Override
    public boolean isEmpty() {
        return this.size() == 0;
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

    private void removeKey(K key) {
        this.data.remove(key);
        this.order.remove(key);
    }
}