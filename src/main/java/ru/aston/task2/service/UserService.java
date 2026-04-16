package ru.aston.task2.service;

import java.util.List;

import ru.aston.task2.dto.UserCreateRequest;
import ru.aston.task2.dto.UserResponse;
import ru.aston.task2.dto.UserUpdateRequest;

/**
 * Интерфейс бизнес-логики для управления пользователями.
 * Предоставляет высокоуровневые операции над пользователями с валидацией и бизнес-правилами.
 */
public interface UserService {

    /**
     * Создает нового пользователя с валидацией данных.
     *
     * @param request данные пользователя для создания
     * @return созданный пользователь с присвоенным ID
     * @throws IllegalArgumentException если данные невалидны
     * @throws RuntimeException         если пользователь с таким email уже существует
     */
    UserResponse createUser(UserCreateRequest request);

    /**
     * Находит пользователя по уникальному идентификатору.
     *
     * @param id идентификатор пользователя
     * @return найденный пользователь
     * @throws IllegalArgumentException если id <= 0
     * @throws RuntimeException         если пользователь не найден
     */
    UserResponse getUserById(Long id);

    /**
     * Возвращает список всех пользователей.
     *
     * @return список пользователей
     */
    List<UserResponse> getAllUsers();

    /**
     * Обновляет данные существующего пользователя.
     *
     * @param id      идентификатор пользователя
     * @param request новые данные пользователя
     * @return обновленный пользователь
     * @throws IllegalArgumentException если данные невалидны или пользователь не существует
     */
    UserResponse updateUser(Long id, UserUpdateRequest request);

    /**
     * Удаляет пользователя по идентификатору.
     *
     * @param id идентификатор пользователя для удаления
     * @throws IllegalArgumentException если id <= 0
     */
    void deleteUser(Long id);
}