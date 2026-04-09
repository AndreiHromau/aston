package ru.aston.task2.containers;

import org.junit.jupiter.api.BeforeAll;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

@Testcontainers
public abstract class DataBaseTestContainer {

    @Container
    protected static final PostgreSQLContainer<?> postgresContainer =
            new PostgreSQLContainer<>("postgres:latest")
                    .withDatabaseName("testdb")
                    .withUsername("testuser")
                    .withPassword("testpass")
                    .withReuse(true);

    @BeforeAll
    static void runContainer() {
        if (!postgresContainer.isRunning()) {
            postgresContainer.start();
        }

        System.setProperty("hibernate.connection.url", postgresContainer.getJdbcUrl());
        System.setProperty("hibernate.connection.username", postgresContainer.getUsername());
        System.setProperty("hibernate.connection.password", postgresContainer.getPassword());
        System.setProperty("hibernate.connection.driver_class", postgresContainer.getDriverClassName());
        System.setProperty("test.config.file", "hibernate-test.cfg.xml");
    }
}