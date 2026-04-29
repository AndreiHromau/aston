package ru.aston.task2.notification;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

@Service
public class NotificationClient {

    private static final Logger log = LoggerFactory.getLogger(NotificationClient.class);

    private final RestClient restClient;

    public NotificationClient(RestClient.Builder builder) {
        this.restClient = builder
                .baseUrl("http://notification-service")
                .build();
    }

    @CircuitBreaker(name = "notificationService", fallbackMethod = "fallback")
    public void sendUserNotification(String operation, String email) {
        restClient.post()
                .uri("/api/notifications/email")
                .body(new SendMailRequest(operation, email))
                .retrieve()
                .toBodilessEntity();
    }

    private void fallback(String operation, String email, Throwable ex) {
        log.warn("Notification service unavailable. operation={} email={}. Reason={}", operation, email, ex.toString());
    }

    public record SendMailRequest(String operation, String email) {
    }
}
