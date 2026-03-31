package ru.aston.task2.dao;

import ru.aston.task2.model.UserEntity;

import java.util.List;
import java.util.Optional;

/**
 * Интерфейс для управления операциями CRUD сущности {@link UserEntity}.
 * Отделяет бизнес-логику от логики доступа к данным.
 */
public interface UserDao {

    /**
     * Сохраняет нового пользователя в базе данных.
     *
     * @param user сущность пользователя для сохранения.
     * @return true, если сохранение прошло успешно, иначе false.
     */
    boolean save(UserEntity user);

    /**
     * Поиск пользователя по уникальному идентификатору.
     *
     * @param id идентификатор пользователя.
     * @return {@link Optional} с найденным пользователем или пустой, если не найден.
     */
    Optional<UserEntity> findById(Long id);

    /**
     * Возвращает список всех зарегистрированных пользователей.
     *
     * @return список объектов {@link UserEntity}.
     */
    List<UserEntity> findAll();

    /**
     * Обновляет данные существующего пользователя.
     *
     * @param user сущность с обновленными данными.
     * @return true, если обновление прошло успешно, иначе false.
     */
    boolean update(UserEntity user);

    /**
     * Удаляет пользователя из базы данных по его идентификатору.
     *
     * @param id идентификатор пользователя для удаления.
     * @return true, если удаление прошло успешно, иначе false.
     */
    boolean deleteById(Long id);
}