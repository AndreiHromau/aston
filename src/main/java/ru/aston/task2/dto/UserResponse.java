package ru.aston.task2.dto;

import java.time.LocalDateTime;

/**
 * DTO для отдачи данных пользователя наружу (например, в HTTP-ответе).
 *
 * @param id        идентификатор пользователя
 * @param name      имя пользователя
 * @param email     email пользователя
 * @param age       возраст пользователя
 * @param createdAt дата и время создания пользователя
 */
public record UserResponse(
        Long id,
        String name,
        String email,
        Integer age,
        LocalDateTime createdAt
) {
}