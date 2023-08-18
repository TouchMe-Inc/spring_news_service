package by.touchme.newsservice.cache.impl;

import by.touchme.newsservice.cache.Cache;

import java.util.Optional;

public class LFUCache<K, V> implements Cache<K, V> {
    private final int capacity;

    public LFUCache(int capacity) {
        this.capacity = capacity;
    }

    @Override
    public void set(K key, V value) {
    }

    @Override
    public Optional<V> get(K key) {
        return Optional.empty();
    }

    @Override
    public int size() {
        return 0;
    }

    @Override
    public boolean isEmpty() {
        return this.size() == 0;
    }

    @Override
    public void clear() {

    }
}