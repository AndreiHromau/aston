package ru.aston.task2.service;

import ru.aston.task2.model.UserEntity;

/**
 * Интерфейс бизнес-логики для управления пользователями.
 * Предоставляет высокоуровневые операции над пользователями с валидацией и бизнес-правилами.
 */
public interface UserService {

    /**
     * Создает нового пользователя с валидацией данных.
     *
     * @param user данные пользователя для создания
     * @return созданный пользователь с присвоенным ID
     * @throws IllegalArgumentException если данные невалидны
     * @throws RuntimeException         если пользователь с таким email уже существует
     */
    UserEntity createUser(UserEntity user);

    /**
     * Находит пользователя по уникальному идентификатору.
     *
     * @param id идентификатор пользователя
     * @return найденный пользователь
     * @throws IllegalArgumentException если id <= 0
     * @throws RuntimeException         если пользователь не найден
     */
    UserEntity getUserById(Long id);

    /**
     * Обновляет данные существующего пользователя.
     *
     * @param user обновленные данные пользователя
     * @return обновленный пользователь
     * @throws IllegalArgumentException если данные невалидны или пользователь не существует
     */
    UserEntity updateUser(UserEntity user);

    /**
     * Удаляет пользователя по идентификатору.
     *
     * @param id идентификатор пользователя для удаления
     * @return true если пользователь был удален, иначе false
     * @throws IllegalArgumentException если id <= 0
     */
    boolean deleteUser(Long id);
}