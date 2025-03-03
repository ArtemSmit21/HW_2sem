package org.example.testcontainer;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@Slf4j
@Testcontainers
public class TestWithTestcontainers {

    @Container
    private static final PostgreSQLContainer<?> postgreSQLContainer = new PostgreSQLContainer<>("postgres:latest")
            .withDatabaseName("test")
            .withUsername("postgres")
            .withPassword("postgres")
            .withInitScript("init.sql");

    @Test
    public void test() {
        log.info(postgreSQLContainer.getJdbcUrl());
        log.info(postgreSQLContainer.getHost());
        log.info(postgreSQLContainer.getFirstMappedPort().toString());
        assertTrue(postgreSQLContainer.isRunning());
    }
}
