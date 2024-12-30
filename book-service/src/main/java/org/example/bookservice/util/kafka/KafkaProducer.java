package org.example.bookservice.util.kafka;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class KafkaProducer {
  private final KafkaTemplate<String, Long> kafkaTemplate;

  public void sendBookCreatedMessage(Long bookId) {
    log.info("send book created message with bookId: {}", bookId);
    kafkaTemplate.send("book-create", bookId);
  }
}
