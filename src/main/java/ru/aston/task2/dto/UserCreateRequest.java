package ru.aston.task2.dto;

public record UserCreateRequest(
        String name,
        String email,
        Integer age
) {
}
