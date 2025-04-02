package org.example.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.kafka.KafkaAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.testcontainers.containers.KafkaContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

@SpringBootTest(
  classes = {KafkaProducerService.class},
  properties = {"topic-to-send-message=audit_topic"}
)
@Import({KafkaAutoConfiguration.class, KafkaProducerServiceTest.ObjectMapperTestConfig.class})
@Testcontainers
public class KafkaProducerServiceTest {

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

  @Autowired
  private KafkaProducerService kafkaProducerService;
  @Autowired
  private ObjectMapper objectMapper;

  @Test
  @DisplayName("This test check send method")
  void test1() {
    String testDtoMessage = new String("abcd");

    assertDoesNotThrow(() -> kafkaProducerService.sendMessage(testDtoMessage));

    KafkaTestConsumer consumer = new KafkaTestConsumer(KAFKA.getBootstrapServers(), "some-group-id");
    consumer.subscribe(List.of("audit_topic"));

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
        assertEquals(testDtoMessage, message);
      }
    );
  }
}