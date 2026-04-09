package ru.aston.task2.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import ru.aston.task2.dao.UserDao;
import ru.aston.task2.model.UserEntity;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class UserServiceImplTest {

    @Mock
    private UserDao userDaoMock;
    @InjectMocks
    private UserServiceImpl userService;
    private UserEntity testUser;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);

        testUser = new UserEntity();
        testUser.setId(1L);
        testUser.setName("Тест");
        testUser.setEmail("test@mail.ru");
    }

    @Test
    void testCreateUser_ShouldCallDaoCreate() {
        userService.createUser(testUser);

        verify(userDaoMock).save(testUser);
    }

    @Test
    void testGetUserById_UserExists_ShouldReturnUser() {
        when(userDaoMock.findById(1L)).thenReturn(Optional.of(testUser));
        UserEntity result = userService.getUserById(1L);

        assertNotNull(result);
        assertEquals("Тест", result.getName());
        verify(userDaoMock).findById(1L);
    }

    @Test
    void testGetUserById_UserDoesNotExist_ShouldReturnNull() {
        when(userDaoMock.findById(999L)).thenReturn(Optional.empty());
        UserEntity result = userService.getUserById(999L);

        assertNull(result);
        verify(userDaoMock).findById(999L);
    }

    @Test
    void testUpdateUser_ShouldReturnUpdatedUser() {
        testUser.setName("Вася");
        userService.updateUser(testUser);

        verify(userDaoMock).update(testUser);
    }

    @Test
    void testDeleteUser_ShouldReturnTrue() {
        when(userDaoMock.deleteById(1L)).thenReturn(true);
        boolean result = userService.deleteUser(1L);

        assertTrue(result);
        verify(userDaoMock).deleteById(1L);
    }
}