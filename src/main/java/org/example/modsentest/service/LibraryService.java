package org.example.modsentest.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.example.modsentest.exception.NotFoundException;
import org.example.modsentest.persistence.entity.Book;
import org.example.modsentest.persistence.entity.LibraryRecord;
import org.example.modsentest.persistence.repository.LibraryRecordRepository;
import org.example.modsentest.util.mapper.LibraryRecordMapper;
import org.example.modsentest.web.response.LibraryRecordResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
public class LibraryService {
  private final LibraryRecordRepository libraryRecordRepository;
  private final LibraryRecordMapper libraryRecordMapper;

  @Transactional
  public void addBookToLibraryRecord(Book book) {

    LibraryRecord libraryRecord = new LibraryRecord(book);

    libraryRecordRepository.save(libraryRecord);
  }

  @Transactional
  public ResponseEntity<List<LibraryRecordResponse>> getAllNoBorrowedBooks() {

    Stream<LibraryRecord> borrowedBookStream = libraryRecordRepository
            .findByBorrowedDateNull()
            .stream();

    return ResponseEntity.ok(borrowedBookStream
            .map(libraryRecordMapper::toResponse)
            .toList());
  }

  @Transactional
  public ResponseEntity<LibraryRecordResponse> borrowBook(Long bookId, LocalDate borrowDate, LocalDate returnDate) {

    LibraryRecord libraryRecord = libraryRecordRepository.findByBookId(bookId)
            .orElseThrow(() -> new NotFoundException("Book not found by id: " + bookId));

    libraryRecord.setBorrowedDate(borrowDate);
    libraryRecord.setReturnedDate(returnDate);

    libraryRecord = libraryRecordRepository.save(libraryRecord);
    return ResponseEntity.ok(libraryRecordMapper.toResponse(libraryRecord));
  }

  @Transactional
  public ResponseEntity<LibraryRecordResponse> releaseBook(Long bookId) {

    LibraryRecord libraryRecord = libraryRecordRepository.findByBookId(bookId)
            .orElseThrow(() -> new NotFoundException("Book not found by id: " + bookId));

    libraryRecord.setBorrowedDate(null);
    libraryRecord.setReturnedDate(null);

    LibraryRecord addedLibraryRecord = libraryRecordRepository.save(libraryRecord);

    return ResponseEntity.ok(libraryRecordMapper.toResponse(addedLibraryRecord));
  }

  @Transactional
  public ResponseEntity<List<LibraryRecordResponse>> getAllBorrowedBooksRecord() {

    return ResponseEntity.ok(libraryRecordRepository
            .findAllBy()
            .stream()
            .filter(libraryRecord -> libraryRecord.getBorrowedDate() != null)
            .map(libraryRecordMapper::toResponse)
            .toList());
  }

  @Transactional
  public ResponseEntity<List<LibraryRecordResponse>> getAllRecord() {

    return ResponseEntity.ok(libraryRecordRepository
            .findAllBy()
            .stream()
            .map(libraryRecordMapper::toResponse)
            .toList());
  }
}
