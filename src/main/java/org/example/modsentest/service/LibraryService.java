package org.example.modsentest.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.example.modsentest.exception.NotFoundException;
import org.example.modsentest.persistence.entity.Book;
import org.example.modsentest.persistence.entity.LibraryRecord;
import org.example.modsentest.persistence.repository.LibraryRecordRepository;
import org.example.modsentest.web.response.LibraryRecordResponse;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
public class LibraryService {
  private final LibraryRecordRepository libraryRecordRepository;
  private final ModelMapper modelMapper;

  @Transactional
  public void addBookToLibraryRecord(Book book) {

    LibraryRecord libraryRecord = new LibraryRecord(book);

    libraryRecordRepository.save(libraryRecord);
    modelMapper.map(libraryRecord, LibraryRecordResponse.class);
  }

  @Transactional
  public ResponseEntity<List<LibraryRecordResponse>> getAllNoBorrowedBooks() {

    Stream<LibraryRecord> borrowedBookStream = libraryRecordRepository
            .streamByBorrowedDateNull();

    return ResponseEntity.ok(borrowedBookStream
            .map((element) -> modelMapper.map(element, LibraryRecordResponse.class))
            .toList());
  }

  @Transactional
  public ResponseEntity<LibraryRecordResponse> borrowBook(Long bookId, LocalDate borrowDate, LocalDate returnDate) {

    LibraryRecord libraryRecord = libraryRecordRepository.findByBookId(bookId)
            .orElseThrow(() -> new NotFoundException("Book not found by id: " + bookId));

    libraryRecord.setBorrowedDate(borrowDate);
    libraryRecord.setReturnedDate(returnDate);

    return ResponseEntity.ok(modelMapper.map(libraryRecord, LibraryRecordResponse.class));
  }

  @Transactional
  public ResponseEntity<LibraryRecordResponse> releaseBook(Long bookId) {

    LibraryRecord libraryRecord = libraryRecordRepository.findByBookId(bookId)
            .orElseThrow(() -> new NotFoundException("Book not found by id: " + bookId));

    libraryRecord.setBorrowedDate(null);
    libraryRecord.setReturnedDate(null);

    LibraryRecord addedLibraryRecord = libraryRecordRepository.save(libraryRecord);
    return ResponseEntity.ok(modelMapper.map(addedLibraryRecord, LibraryRecordResponse.class));
  }

  @Transactional
  public ResponseEntity<List<LibraryRecordResponse>> getAllBorrowedBooksRecord() {

    return ResponseEntity.ok(libraryRecordRepository
            .streamAllBy()
            .filter(libraryRecord -> libraryRecord.getBorrowedDate() != null)
            .map((element) -> modelMapper.map(element, LibraryRecordResponse.class))
            .toList());
  }

  @Transactional
  public ResponseEntity<List<LibraryRecordResponse>> getAllRecord() {

    return ResponseEntity.ok(libraryRecordRepository
            .streamAllBy()
            .map((element) -> modelMapper.map(element, LibraryRecordResponse.class))
            .toList());
  }
}
