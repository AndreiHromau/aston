package ru.aston.task1;

/**
 * Кастомная реализация HashMap для интенсива Aston.
 * Реализованы методы: put, get, remove, size, isEmpty, containsKey + dynamic resize.
 * * @param <K> тип ключей
 * * @param <V> тип значений
 *
 * @author Andrei Hromau
 * @version 1.0
 */
public class MyHashMap<K, V> implements MyMap<K, V> {
    private Node<K, V>[] table;
    private int size = 0;
    private static final int INITIAL_CAPACITY = 16;

    private static class Node<K, V> {
        K key;
        V value;
        Node<K, V> next;

        Node(K key, V value, Node<K, V> next) {
            this.key = key;
            this.value = value;
            this.next = next;
        }
    }

    public MyHashMap() {
        table = new Node[INITIAL_CAPACITY];
    }

    private int getIndex(K key) {
        if (key == null) {
            return 0;
        }
        int hash = key.hashCode();
        if (hash < 0) {
            hash = hash * -1;
        }
        return hash % table.length;
    }

    public void put(K key, V value) {
        if (size >= table.length) {
            increaseCapacity();
        }
        int index = getIndex(key);
        Node<K, V> current = table[index];
        while (current != null) {
            if (current.key == key || (current.key != null && current.key.equals(key))) {
                current.value = value;
                return;
            }
            current = current.next;
        }
        table[index] = new Node<>(key, value, table[index]);
        size++;
    }

    private void increaseCapacity() {
        Node<K, V>[] oldTable = table;
        table = new Node[oldTable.length * 2];
        size = 0;

        for (int i = 0; i < oldTable.length; i++) {
            Node<K, V> node = oldTable[i];
            while (node != null) {
                put(node.key, node.value);
                node = node.next;
            }
        }
    }

    public V get(K key) {
        int index = getIndex(key);
        Node<K, V> current = table[index];
        while (current != null) {
            if (current.key == key || (current.key != null && current.key.equals(key))) {
                return current.value;
            }
            current = current.next;
        }
        return null;
    }

    public void remove(K key) {
        int index = getIndex(key);
        Node<K, V> current = table[index];
        Node<K, V> prev = null;

        while (current != null) {
            if (current.key == key || (current.key != null && current.key.equals(key))) {
                if (prev == null) {
                    table[index] = current.next;
                } else {
                    prev.next = current.next;
                }
                size--;
                return;
            }
            prev = current;
            current = current.next;
        }
    }

    public int size() {
        return size;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public boolean containsKey(K key) {
        return get(key) != null;
    }
}