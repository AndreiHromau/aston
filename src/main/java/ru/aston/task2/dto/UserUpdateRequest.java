package ru.aston.task2.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

/**
 * DTO для обновления данных пользователя.
 *
 * @param name  новое имя пользователя (не пустое, максимум 100 символов)
 * @param email новый email пользователя (не пустой, валидный формат, максимум 255 символов)
 * @param age   новый возраст пользователя (обязателен, 1..150)
 */
public record UserUpdateRequest(
        @NotBlank(message = "Имя не может быть пустым")
        @Size(max = 100, message = "Имя не должно превышать 100 символов")
        String name,

        @NotBlank(message = "Email не может быть пустым")
        @Email(message = "Некорректный формат email")
        @Size(max = 255, message = "Email не должен превышать 255 символов")
        String email,

        @NotNull(message = "Возраст обязателен")
        @Min(value = 1, message = "Возраст должен быть не менее 1")
        @Max(value = 150, message = "Возраст должен быть не более 150")
        Integer age
) {
}