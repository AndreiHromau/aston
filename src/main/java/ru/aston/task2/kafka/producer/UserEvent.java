package ru.aston.task2.kafka.producer;

public record UserEvent(
        String operation,
        String email
) {
}
