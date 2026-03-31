package ru.aston.task2;

import lombok.extern.slf4j.Slf4j;
import ru.aston.task2.dao.UserDao;
import ru.aston.task2.dao.UserDaoImpl;
import ru.aston.task2.model.UserEntity;
import ru.aston.task2.util.HibernateUtil;

import java.util.Scanner;

@Slf4j
public class UserServiceApp {
    private static final UserDao USER_DAO = new UserDaoImpl();
    private static final Scanner SCANNER = new Scanner(System.in);

    public static void main(String[] args) {
        log.info("Взаимодействие: Приложение запущено");

        while (true) {
            printMenu();
            String input = SCANNER.nextLine();

            switch (input) {
                case "1" -> createUser();
                case "2" -> findUser();
                case "3" -> USER_DAO.findAll().forEach(System.out::println);
                case "4" -> updateUser();
                case "5" -> deleteUser();
                case "0" -> {
                    HibernateUtil.shutdown();
                    log.info("Взаимодействие: Завершение работы");
                    return;
                }
                default -> log.warn("Предупреждение: Некорректный ввод: {}", input);
            }
        }
    }

    private static void printMenu() {
        System.out.println("\n--- Управление пользователями ---");
        System.out.println("1. Создать | 2. Поиск | 3. Все | 4. Обновить | 5. Удалить | 0. Выход");
        System.out.print("Выбор: ");
    }

    private static void createUser() {
        try {
            UserEntity user = new UserEntity();
            System.out.print("Имя: ");
            user.setName(SCANNER.nextLine());
            System.out.print("Email: ");
            user.setEmail(SCANNER.nextLine());
            System.out.print("Возраст: ");
            user.setAge(Integer.parseInt(SCANNER.nextLine()));

            if (USER_DAO.save(user)) System.out.println("Пользователь успешно создан");
        } catch (Exception e) {
            log.warn("Предупреждение: Ошибка ввода данных: {}", e.getMessage());
        }
    }

    private static void findUser() {
        try {
            System.out.print("ID: ");
            Long id = Long.parseLong(SCANNER.nextLine());
            USER_DAO.findById(id).ifPresentOrElse(System.out::println,
                    () -> System.out.println("Пользователь не найден"));
        } catch (Exception e) {
            log.warn("Предупреждение: Неверный формат ID");
        }
    }

    private static void updateUser() {
        try {
            System.out.print("ID для обновления: ");
            Long id = Long.parseLong(SCANNER.nextLine());
            USER_DAO.findById(id).ifPresent(user -> {
                System.out.print("Новое имя [" + user.getName() + "]: ");
                user.setName(SCANNER.nextLine());
                USER_DAO.update(user);
            });
        } catch (Exception e) {
            log.warn("Предупреждение: Ошибка при обновлении: {}", e.getMessage());
        }
    }

    private static void deleteUser() {
        try {
            System.out.print("ID для удаления: ");
            Long id = Long.parseLong(SCANNER.nextLine());
            USER_DAO.deleteById(id);
        } catch (Exception e) {
            log.warn("Предупреждение: Ошибка при удалении");
        }
    }
}