package ru.aston.task2.dao;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import ru.aston.task2.containers.DataBaseTestContainer;
import ru.aston.task2.model.UserEntity;
import ru.aston.task2.util.HibernateUtil;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class UserDaoImplTest extends DataBaseTestContainer {

    private UserDao userDao;

    @BeforeEach
    void setUp() {
        userDao = new UserDaoImpl();
    }

    @AfterEach
    void tearDown() {
        try (var session = HibernateUtil.getSessionFactory().openSession()) {
            var transaction = session.beginTransaction();
            session.createMutationQuery("DELETE FROM UserEntity").executeUpdate();
            transaction.commit();
        }
    }

    @Test
    @Order(1)
    @DisplayName("Сохранение пользователя в БД")
    void testSave() {
        UserEntity user = createTestUser("test@example.com", "Test User", 25);

        assertTrue(userDao.save(user));
        assertNotNull(user.getId());
    }

    @Test
    @Order(2)
    @DisplayName("Поиск существующего пользователя по ID")
    void testFindByIdWhenExists() {
        UserEntity user = createTestUser("test@example.com", "Test User", 25);
        userDao.save(user);

        Optional<UserEntity> found = userDao.findById(user.getId());

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
        userDao.save(user);

        Optional<UserEntity> found = userDao.findById(user.getId());
        assertTrue(found.isPresent());
        assertEquals("Вася", found.get().getName());

        UserEntity vas = found.get();
        vas.setName("Василий");
        assertTrue(userDao.update(vas));

        assertEquals("Василий", userDao.findById(vas.getId()).get().getName());
    }

    @Test
    @Order(4)
    @DisplayName("Получение всех пользователей")
    void testFindAll() {
        for (int i = 1; i <= 5; i++) {
            UserEntity user = createTestUser(String.format("test%d@mail.ru", i), String.format("Test %d", i), 20 + i);
            userDao.save(user);
        }

        assertFalse(userDao.findAll().isEmpty());
    }

    @Test
    @Order(5)
    @DisplayName("Удаление пользователя")
    void testDeleteByIdWhenExists() {
        UserEntity user = createTestUser("masha@mail.ru", "Маша", 32);
        userDao.save(user);
        long id = user.getId();

        assertTrue(userDao.deleteById(id));
        assertFalse(userDao.findById(id).isPresent());
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