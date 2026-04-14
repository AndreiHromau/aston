package ru.aston.task2.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.aston.task2.entity.UserEntity;

/**
 * Репозиторий для работы с сущностью {@link UserEntity}.
 * <p>
 * Расширяет {@link JpaRepository} и предоставляет базовые CRUD-операции.
 */
public interface UserRepository extends JpaRepository<UserEntity, Long> {
}