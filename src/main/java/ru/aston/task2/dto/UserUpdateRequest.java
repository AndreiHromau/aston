package ru.aston.task2.dto;

public record UserUpdateRequest(
        String name,
        String email,
        Integer age
) {
}
