package by.touchme.newsservice.cache.impl;

import by.touchme.newsservice.cache.Cache;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class LFUCache<K, V> implements Cache<K, V> {
    private final int capacity;

    private final Map<K, V> data;

    private final Map<K, Integer> frequency;

    private final ReentrantReadWriteLock lock;

    public LFUCache(int capacity) {
        this.capacity = capacity;
        this.data = new ConcurrentHashMap<>(capacity);
        this.frequency = new ConcurrentHashMap<>(capacity);
        this.lock = new ReentrantReadWriteLock();
    }

    @Override
    public void put(K key, V value) {
        this.lock.writeLock().lock();

        try {
            int frequency = 1;

            if (this.data.containsKey(key)) {
                frequency += this.frequency.get(key);
            } else if (this.size() >= this.capacity) {
                // Add Tree for O(1) and order for remove only old record
                Map.Entry<K, Integer> removedEntry = Collections.min(
                        this.frequency.entrySet(),
                        Map.Entry.comparingByValue()
                );
                this.remove(removedEntry.getKey());
            }

            this.data.put(key, value);
            this.frequency.put(key, frequency);
        } finally {
            this.lock.writeLock().unlock();
        }
    }

    @Override
    public Optional<V> get(K key) {
        this.lock.readLock().lock();

        try {
            if (!this.data.containsKey(key) || this.isEmpty()) {
                return Optional.empty();
            }

            this.frequency.put(key, this.frequency.get(key) + 1);

            return Optional.of(this.data.get(key));
        } finally {
            this.lock.readLock().unlock();
        }
    }

    @Override
    public void remove(K key) {
        this.data.remove(key);
        this.frequency.remove(key);
    }

    @Override
    public int size() {
        this.lock.readLock().lock();

        try {
            return this.data.size();
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
            this.frequency.clear();
        } finally {
            this.lock.writeLock().unlock();
        }
    }
}