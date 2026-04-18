package ru.aston.task2.kafka;

public record UserEvent(
        String operation,
        String email
) {
}
