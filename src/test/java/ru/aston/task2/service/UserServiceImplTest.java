package ru.aston.task2.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.aston.task2.model.UserEntity;
import ru.aston.task2.repository.UserRepository;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {
    @Mock
    private UserRepository userRepositoryMock;

    @InjectMocks
    private UserServiceImpl userService;
    private UserEntity testUser;

    @BeforeEach
    void setup() {
        testUser = new UserEntity();
        testUser.setId(1L);
        testUser.setName("Тест");
        testUser.setEmail("test@mail.ru");
    }

    @Test
    void testCreateUser_ShouldCallDaoCreate() {
        when(userRepositoryMock.save(testUser)).thenReturn(testUser);
        userService.createUser(testUser);

        verify(userRepositoryMock).save(testUser);
    }

    @Test
    void testGetUserById_UserExists_ShouldReturnUser() {
        when(userRepositoryMock.findById(1L)).thenReturn(Optional.of(testUser));
        UserEntity result = userService.getUserById(1L);

        assertNotNull(result);
        assertEquals("Тест", result.getName());
        verify(userRepositoryMock).findById(1L);
    }

    @Test
    void testGetUserById_UserDoesNotExist_ShouldReturnNull() {
        when(userRepositoryMock.findById(999L)).thenReturn(Optional.empty());
        UserEntity result = userService.getUserById(999L);

        assertNull(result);
        verify(userRepositoryMock).findById(999L);
    }

    @Test
    void testUpdateUser_ShouldReturnUpdatedUser() {
        testUser.setName("Вася");
        when(userRepositoryMock.save(any(UserEntity.class))).thenReturn(testUser);
        userService.updateUser(testUser);

        verify(userRepositoryMock).save(testUser);
    }

    @Test
    void testDeleteUser_ShouldReturnTrue() {
        when(userRepositoryMock.existsById(1L)).thenReturn(true);
        boolean result = userService.deleteUser(1L);

        assertTrue(result);
        verify(userRepositoryMock).existsById(1L);
        verify(userRepositoryMock).deleteById(1L);
    }
}