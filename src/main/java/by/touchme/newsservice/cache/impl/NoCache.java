package by.touchme.newsservice.cache.impl;

import by.touchme.newsservice.cache.Cache;

import java.util.Optional;

public class NoCache<K, V> implements Cache<K, V> {

    public NoCache(int capacity) {
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