package org.example.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.example.SpringApp;
import org.example.model.OutboxRecord;
import org.example.repository.OutboxRepository;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.kafka.KafkaAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.dao.DataIntegrityViolationException;
import org.testcontainers.containers.KafkaContainer;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(
  classes = {SpringApp.class},
  properties = {"topic-to-send-message=audit_topic", "spring.flyway.baseline-on-migrate=true"}
)
@Import({KafkaAutoConfiguration.class, KafkaProducerServiceTest.ObjectMapperTestConfig.class})
@Testcontainers
public class OutboxSchedulerTest {

  private static final DockerImageName KAFKA_TEST_IMAGE = DockerImageName.parse("confluentinc/cp-kafka:7.4.0")
    .asCompatibleSubstituteFor("apache/kafka");

  @TestConfiguration
  static class ObjectMapperTestConfig {
    @Bean
    public ObjectMapper objectMapper() {
      return new ObjectMapper();
    }
  }

  @Container
  @ServiceConnection
  public static final KafkaContainer KAFKA = new KafkaContainer(KAFKA_TEST_IMAGE);

  @Container
  @ServiceConnection
  private static final PostgreSQLContainer<?> POSTGRES = new PostgreSQLContainer<>("postgres:13")
    .withExposedPorts(5432);

  @Autowired
  private OutboxRepository outboxRepository;
  @Autowired
  private OutboxScheduler outboxScheduler;
  @Autowired
  private ObjectMapper objectMapper;

  private static KafkaTestConsumer consumer;

  @BeforeAll
  static void setUp() {
    consumer = new KafkaTestConsumer(KAFKA.getBootstrapServers(), "some-group-id");
    consumer.subscribe(List.of("audit_topic"));
  }

  @Test
  @DisplayName("This test check outbox scheduler")
  void shouldSendCustomMessageToKafka() throws InterruptedException, JsonProcessingException {

    outboxRepository.save(OutboxRecord.builder().data(objectMapper.writeValueAsString("abcd")).build());

    Thread.sleep(10001);

    ConsumerRecords<String, String> records = consumer.poll();
    assertEquals(1, records.count());
    records.iterator().forEachRemaining(
      record -> {
        String message = null;
        try {
          message = objectMapper.readValue(record.value(), String.class);
        } catch (JsonProcessingException e) {
          throw new RuntimeException(e);
        }
        assertEquals("abcd", message);
      }
    );
  }

  @Test
  @DisplayName("Invalid data")
  void shouldThrowExceptionSavingInvalidData() throws InterruptedException {
    assertThrows(
      DataIntegrityViolationException.class, () -> outboxRepository.save(OutboxRecord.builder().data(null).build())
    );

    Thread.sleep(10001);

    ConsumerRecords<String, String> records = consumer.poll();

    assertEquals(0, records.count());
  }
}