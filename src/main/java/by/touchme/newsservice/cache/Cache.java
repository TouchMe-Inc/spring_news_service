package by.touchme.newsservice.cache;

import java.util.Optional;

public interface Cache<K, V> {

    void set(K key, V value);

    Optional<V> get(K key);

    int size();

    boolean isEmpty();

    void clear();
}