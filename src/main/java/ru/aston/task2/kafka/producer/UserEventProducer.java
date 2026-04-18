package ru.aston.task2.kafka.producer;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class UserEventProducer {

    private final KafkaTemplate<String, ru.aston.task2.kafka.producer.UserEvent> kafkaTemplate;
    private final String topic;

    public UserEventProducer(
            KafkaTemplate<String, ru.aston.task2.kafka.producer.UserEvent> kafkaTemplate,
            @Value("${app.kafka.user-events-topic}") String topic
    ) {
        this.kafkaTemplate = kafkaTemplate;
        this.topic = topic;
    }

    public void sendUserCreated(String email) {
        kafkaTemplate.send(topic, email, new ru.aston.task2.kafka.producer.UserEvent("CREATE", email));
    }

    public void sendUserDeleted(String email) {
        kafkaTemplate.send(topic, email, new ru.aston.task2.kafka.producer.UserEvent("DELETE", email));
    }
}
