package org.example.libraryservice.util.kafka;

import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class KafkaConsumer {

  @KafkaListener(topics = "book-create", groupId = "library-service-consumer")
  public void bookCreateListen(Long bookId) {
    log.info("bookCreateListen " + bookId);
  }
}
