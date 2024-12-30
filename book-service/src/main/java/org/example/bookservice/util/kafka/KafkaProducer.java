package org.example.bookservice.util.kafka;

import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class KafkaProducer {
  private final KafkaTemplate<String, Long> kafkaTemplate;

  public void sendBookCreatedMessage(Long bookId) {
    kafkaTemplate.send("book-created", bookId);
  }
}
