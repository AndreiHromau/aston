package ru.aston.task1;

/**
 * Кастомная реализация HashMap для интенсива Aston.
 * Реализованы методы: put, get, remove, size, isEmpty, containsKey + dynamic resize.
 *
 * @author Andrei Hromau
 * @version 1.0
 */
public class MyHashMap<K, V> {
    private Node<K, V>[] table;
    private int size = 0;

    private static class Node<K, V> {
        //поля
        K key;
        V value;
        Node<K, V> next;

        //конструктор
        Node(K key, V value, Node<K, V> next) {
            this.key = key;
            this.value = value;
            this.next = next;
        }
    }

    public MyHashMap() {
        table = new Node[16];
    }

    // определяем, в какой bucket попадет ключ
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

    // 1.метод положить в корзину + ресайз, который вызовем отдельно
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

    /* Исполнение ресайза в отдельный метод, чтобы не объединять описание логики
       put + ресайз (согласно CLEAN CODE), а лишь вызов метода increaseCapacity() */
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

    // 2.Метод получение значения по ключу
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

    // 3.Метод удаления ключа
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

    // 4.Метод получение фактического размера
    public int size() {
        return size;
    }

    // 5.Метод проверки на пустоту
    public boolean isEmpty() {
        return size == 0;
    }

    // 6.Метод, проверяющий наличие ключа
    public boolean containsKey(K key) {
        return get(key) != null;
    }
}