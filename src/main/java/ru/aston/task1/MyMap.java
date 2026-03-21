package ru.aston.task1;

public interface MyMap<K, V> {
    void put(K key, V value);

    V get(K key);

    void remove(K key);

    int size();

    boolean isEmpty();

    boolean containsKey(K key);
}
