package org.example.service;

import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Timer;
import org.example.model.OutboxRecord;
import org.example.repository.OutboxRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.util.List;
import java.util.concurrent.CompletableFuture;

@Service
public class OutboxScheduler {
  private final KafkaTemplate<String, String> kafkaTemplate;

  private final String topic;

  private final OutboxRepository outboxRepository;

  private final Timer timer;

  public OutboxScheduler(
    KafkaTemplate<String, String> kafkaTemplate,
    @Value("${topic-to-send-message}") String topic,
    OutboxRepository outboxRepository,
    MeterRegistry registry) {
    this.kafkaTemplate = kafkaTemplate;
    this.topic = topic;
    this.outboxRepository = outboxRepository;
    this.timer = Timer.builder("cdc.outbox.execution.time")
      .description("Time taken to process CDC outbox")
      .publishPercentiles(0.5, 0.95, 0.99)
      .publishPercentileHistogram()
      .maximumExpectedValue(Duration.ofSeconds(10))
      .register(registry);
  }

  @Transactional
  @Scheduled(fixedDelay = 100)
  public void processOutbox() {
    timer.record(() -> {
      List<OutboxRecord> result = outboxRepository.findAll();
      for (OutboxRecord outboxRecord : result) {
        CompletableFuture<SendResult<String, String>> sendResult = kafkaTemplate.send(topic, outboxRecord.getData());
      }
      outboxRepository.deleteAll(result);
    });
  }
}
