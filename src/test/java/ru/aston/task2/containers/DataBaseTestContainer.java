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

        System.setProperty("spring.datasource.url", postgresContainer.getJdbcUrl());
        System.setProperty("spring.datasource.username", postgresContainer.getUsername());
        System.setProperty("spring.datasource.password", postgresContainer.getPassword());
        System.setProperty("spring.datasource.driver-class-name", postgresContainer.getDriverClassName());
        System.setProperty("spring.jpa.hibernate.ddl-auto", "create-drop");
    }
}