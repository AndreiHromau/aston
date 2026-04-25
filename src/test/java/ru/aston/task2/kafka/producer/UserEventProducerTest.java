package ru.aston.task2.kafka.producer;

import org.junit.jupiter.api.Test;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.apache.kafka.clients.producer.MockProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.kafka.support.serializer.JsonSerializer;
import ru.aston.task2.kafka.dto.UserEvent;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

class UserEventProducerTest {

    @Test
    void sendUserCreated_sendsCreateEvent() {
        // given
        String topic = "user-events";
        String email = "ivan@mail.ru";

        MockProducer<String, UserEvent> mockProducer = new MockProducer<>(true, new StringSerializer(), new JsonSerializer<>());
        ProducerFactory<String, UserEvent> producerFactory = new SingleProducerFactory<>(mockProducer);
        KafkaTemplate<String, UserEvent> kafkaTemplate = new KafkaTemplate<>(producerFactory);
        UserEventProducer producer = new UserEventProducer(kafkaTemplate, topic);

        // when
        producer.sendUserCreated(email);

        // then
        assertFalse(mockProducer.history().isEmpty());
        ProducerRecord<String, UserEvent> record = mockProducer.history().getFirst();
        assertEquals(topic, record.topic());
        assertEquals(email, record.key());
        assertEquals(UserEvent.Operation.CREATE, record.value().operation());
        assertEquals(email, record.value().email());
    }

    @Test
    void sendUserDeleted_sendsDeleteEvent() {
        // given
        String topic = "user-events";
        String email = "ivan@mail.ru";

        MockProducer<String, UserEvent> mockProducer = new MockProducer<>(true, new StringSerializer(), new JsonSerializer<>());
        ProducerFactory<String, UserEvent> producerFactory = new SingleProducerFactory<>(mockProducer);
        KafkaTemplate<String, UserEvent> kafkaTemplate = new KafkaTemplate<>(producerFactory);
        UserEventProducer producer = new UserEventProducer(kafkaTemplate, topic);

        // when
        producer.sendUserDeleted(email);

        // then
        assertFalse(mockProducer.history().isEmpty());
        ProducerRecord<String, UserEvent> record = mockProducer.history().getFirst();
        assertEquals(topic, record.topic());
        assertEquals(email, record.key());
        assertEquals(UserEvent.Operation.DELETE, record.value().operation());
        assertEquals(email, record.value().email());
    }

    private static final class SingleProducerFactory<K, V> implements ProducerFactory<K, V> {
        private final Producer<K, V> producer;

        private SingleProducerFactory(Producer<K, V> producer) {
            this.producer = producer;
        }

        @Override
        public Producer<K, V> createProducer() {
            return producer;
        }
    }
}