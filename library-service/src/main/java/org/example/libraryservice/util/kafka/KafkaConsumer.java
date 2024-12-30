package org.example.libraryservice.util.kafka;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.libraryservice.service.LibraryRecordService;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class KafkaConsumer {
  private final LibraryRecordService libraryRecordService;

  @KafkaListener(topics = "book-create", groupId = "library-service-consumer")
  public void bookCreateListen(Long bookId) {
    log.info("add book to library record with id: {}", bookId);
    libraryRecordService.addBookToLibraryRecord(bookId);
  }
}
