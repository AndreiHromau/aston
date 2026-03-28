package ru.aston.task1;

public class Main {
    public static void main(String[] args) {
        MyMap<String, Integer> map = new MyHashMap<>();
        System.out.println("Мапа пустая? " + map.isEmpty());

        map.put("Java", 100);
        map.put("Kotlin", 90);
        map.put("Spring", 80);
        System.out.println("Размер после добавления 3-х элементов: " + map.size());
        System.out.println("Значение для 'Java': " + map.get("Java"));

        map.put("Java", 150);
        System.out.println("Обновленное значение для 'Java': " + map.get("Java"));

        map.put(null, 500);
        System.out.println("Значение для null-ключа: " + map.get(null));
        System.out.println("Содержит ли null-ключ? " + map.containsKey(null));

        for (int i = 0; i < 20; i++) {
            map.put("Key_" + i, i);
        }
        System.out.println("Фактический размер после for: " + map.size());

        map.put("Key_10", 10);
        System.out.println("Получаем ключ" + map.get("Key_10"));
        map.remove("Key_10");
        System.out.println("Результат после удаления Key_10: " + map.get("Key_10"));
    }
}