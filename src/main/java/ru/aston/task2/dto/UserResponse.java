package ru.aston.task2.dto;

import java.time.LocalDateTime;

public record UserResponse(
        Long id,
        String name,
        String email,
        Integer age,
        LocalDateTime createdAt
) {
}
