package ru.aston.task2.kafka.producer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import ru.aston.task2.kafka.dto.UserEvent;

@Service
public class UserEventProducer {

    private static final Logger log = LoggerFactory.getLogger(UserEventProducer.class);

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
        UserEvent event = new UserEvent(UserEvent.Operation.CREATE, email);
        log.info("Sending Kafka event: operation={}, email={}, topic={}", event.operation(), event.email(), topic);

        kafkaTemplate.send(topic, email, event)
                .whenComplete((result, ex) -> {
                    if (ex != null) {
                        log.error("Failed to send Kafka event: operation={}, email={}, topic={}",
                                event.operation(), event.email(), topic, ex);
                        return;
                    }

                    var md = result.getRecordMetadata();
                    log.info("Kafka event sent: operation={}, email={}, topic={}, partition={}, offset={}",
                            event.operation(), event.email(), md.topic(), md.partition(), md.offset());
                });
    }

    public void sendUserDeleted(String email) {
        UserEvent event = new UserEvent(UserEvent.Operation.DELETE, email);
        log.info("Sending Kafka event: operation={}, email={}, topic={}", event.operation(), event.email(), topic);

        kafkaTemplate.send(topic, email, event)
                .whenComplete((result, ex) -> {
                    if (ex != null) {
                        log.error("Failed to send Kafka event: operation={}, email={}, topic={}",
                                event.operation(), event.email(), topic, ex);
                        return;
                    }

                    var md = result.getRecordMetadata();
                    log.info("Kafka event sent: operation={}, email={}, topic={}, partition={}, offset={}",
                            event.operation(), event.email(), md.topic(), md.partition(), md.offset());
                });
    }
}