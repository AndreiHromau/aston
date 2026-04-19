package ru.aston.task2.kafka.dto;

import ru.aston.task2.kafka.producer.UserEventProducer;

/**
 * DTO-событие для Kafka, отправляемое {@link UserEventProducer} при создании или удалении пользователя.
 *
 * <p>Содержит информацию об операции и email пользователя.</p>
 *
 * @param operation тип операции над пользователем (например, CREATE или DELETE)
 * @param email     email пользователя, над которым выполнена операция
 */
public record UserEvent(
        String operation,
        String email
) {
}