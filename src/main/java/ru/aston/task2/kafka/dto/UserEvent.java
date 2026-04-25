package ru.aston.task2.kafka.dto;

import ru.aston.task2.kafka.producer.UserEventProducer;

/**
 * DTO-событие для Kafka, отправляемое {@link UserEventProducer} при создании или удалении пользователя.
 *
 * <p>Содержит информацию об операции и email пользователя.</p>
 *
 * @param operation операция над пользователем ({@link UserEvent.Operation})
 * @param email     email пользователя, над которым выполнена операция
 */
public record UserEvent(
        Operation operation,
        String email
) {

    public enum Operation {
        CREATE,
        DELETE
    }
}