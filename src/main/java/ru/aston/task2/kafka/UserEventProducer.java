package ru.aston.task2.kafka;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class UserEventProducer {

    private final KafkaTemplate<String, UserEvent> kafkaTemplate;
    private final String topic;

    public UserEventProducer(
            KafkaTemplate<String, UserEvent> kafkaTemplate,
            @Value("${app.kafka.user-events-topic}") String topic
    ) {
        this.kafkaTemplate = kafkaTemplate;
        this.topic = topic;
    }

    public void sendUserCreated(String email) {
        kafkaTemplate.send(topic, email, new UserEvent("CREATE", email));
    }

    public void sendUserDeleted(String email) {
        kafkaTemplate.send(topic, email, new UserEvent("DELETE", email));
    }
}
