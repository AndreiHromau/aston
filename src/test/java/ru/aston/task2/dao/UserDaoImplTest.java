package ru.aston.task2.dao;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.aston.task2.containers.DataBaseTestContainer;
import ru.aston.task2.model.UserEntity;
import ru.aston.task2.repository.UserRepository;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@SpringBootTest
class UserDaoImplTest extends DataBaseTestContainer {

    @Autowired
    private UserRepository userRepository;

    @Test
    @Order(1)
    @DisplayName("Сохранение пользователя в БД")
    void testSave() {
        UserEntity user = createTestUser("test@example.com", "Test User", 25);

        userRepository.save(user);
        assertNotNull(user.getId());
    }

    @Test
    @Order(2)
    @DisplayName("Поиск существующего пользователя по ID")
    void testFindByIdWhenExists() {
        UserEntity user = createTestUser("test@example.com", "Test User", 25);
        userRepository.save(user);

        Optional<UserEntity> found = userRepository.findById(user.getId());

        assertTrue(found.isPresent());
        assertEquals(user.getEmail(), found.get().getEmail());
        assertEquals(user.getName(), found.get().getName());
        assertEquals(user.getAge(), found.get().getAge());
    }

    @Test
    @Order(3)
    @DisplayName("Обновление пользователя")
    void testUpdate() {
        UserEntity user = createTestUser("vasya@mail.ru", "Вася", 20);
        userRepository.save(user);

        Optional<UserEntity> found = userRepository.findById(user.getId());
        assertTrue(found.isPresent());
        assertEquals("Вася", found.get().getName());

        UserEntity vas = found.get();
        vas.setName("Василий");
        userRepository.save(vas);

        assertEquals("Василий", userRepository.findById(vas.getId()).get().getName());
    }

    @Test
    @Order(4)
    @DisplayName("Получение всех пользователей")
    void testFindAll() {
        createTestUsers(5);

        assertFalse(userRepository.findAll().isEmpty());
    }

    @Test
    @Order(5)
    @DisplayName("Удаление пользователя")
    void testDeleteByIdWhenExists() {
        UserEntity user = createTestUser("masha@mail.ru", "Маша", 32);
        userRepository.save(user);
        long id = user.getId();

        userRepository.deleteById(id);
        assertFalse(userRepository.findById(id).isPresent());
    }

    private void createTestUsers(int count) {
        for (int i = 1; i <= count; i++) {
            UserEntity user = createTestUser(String.format("test%d@mail.ru", i), String.format("Test %d", i), 20 + i);
            userRepository.save(user);
        }
    }

    private UserEntity createTestUser(String email, String name, int age) {
        UserEntity user = new UserEntity();
        user.setEmail(email);
        user.setName(name);
        user.setAge(age);
        user.setCreatedAt(LocalDateTime.now());
        return user;
    }
}