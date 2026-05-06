package ru.aston.task2.dto;

import io.swagger.v3.oas.annotations.media.Schema;

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
@Schema(description = "DTO для отдачи данных пользователя")
public record UserResponse(
        @Schema(description = "Идентификатор пользователя", example = "1")
        Long id,
        @Schema(description = "Имя пользователя", example = "Andrei")
        String name,
        @Schema(description = "Email пользователя", example = "andrei@test.com")
        String email,
        @Schema(description = "Возраст пользователя", example = "25", minimum = "1", maximum = "150")
        Integer age,
        @Schema(description = "Дата и время создания пользователя", example = "2026-05-06T18:30:00")
        LocalDateTime createdAt
) {
}