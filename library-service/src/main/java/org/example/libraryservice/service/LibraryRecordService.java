package org.example.libraryservice.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.example.libraryservice.exception.NotFoundException;
import org.example.libraryservice.persistence.entity.Book;
import org.example.libraryservice.persistence.entity.LibraryRecord;
import org.example.libraryservice.persistence.repository.LibraryRecordRepository;
import org.example.libraryservice.util.mapper.BookMapper;
import org.example.libraryservice.util.mapper.LibraryRecordMapper;
import org.example.libraryservice.web.request.BookRequest;
import org.example.libraryservice.web.response.LibraryRecordResponse;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
public class LibraryRecordService {
  private final LibraryRecordRepository libraryRecordRepository;
  private final LibraryRecordMapper libraryRecordMapper;
  private final BookMapper bookMapper;
  private final static String NOT_FOUND_RECORD_MESSAGE = "Book not found by id: %d";

  @Transactional
  public void addBookToLibraryRecord(BookRequest bookRequest, Long bookId) {

    Book book = bookMapper.requestToEntity(bookRequest);
    book.setId(bookId);
    LibraryRecord libraryRecord = LibraryRecord
            .builder()
            .book(book)
            .build();

    libraryRecordRepository.save(libraryRecord);
  }

  @Transactional
  public List<LibraryRecordResponse> getAllNoBorrowedBooks() {

    Stream<LibraryRecord> borrowedBookStream = libraryRecordRepository
            .findByBorrowedDateNull()
            .stream();

    return borrowedBookStream
            .map(libraryRecordMapper::toResponse)
            .toList();
  }

  @Transactional
  public LibraryRecordResponse borrowBook(Long recordId, LocalDate borrowDate, LocalDate returnDate) {

    LibraryRecord libraryRecord = libraryRecordRepository.findById(recordId)
            .orElseThrow(() -> new NotFoundException(String.format(NOT_FOUND_RECORD_MESSAGE, recordId)));

    libraryRecord.setBorrowedDate(borrowDate);
    libraryRecord.setReturnedDate(returnDate);

    libraryRecord = libraryRecordRepository.save(libraryRecord);
    return libraryRecordMapper.toResponse(libraryRecord);
  }

  @Transactional
  public LibraryRecordResponse releaseBook(Long recordId) {

    LibraryRecord libraryRecord = libraryRecordRepository.findById(recordId)
            .orElseThrow(() -> new NotFoundException(String.format(NOT_FOUND_RECORD_MESSAGE, recordId)));

    libraryRecord.setBorrowedDate(null);
    libraryRecord.setReturnedDate(null);

    LibraryRecord addedLibraryRecord = libraryRecordRepository.save(libraryRecord);

    return libraryRecordMapper.toResponse(addedLibraryRecord);
  }

  @Transactional
  public List<LibraryRecordResponse> getAllBorrowedBooksRecord() {

    return libraryRecordRepository
            .findAllBy()
            .stream()
            .filter(libraryRecord -> libraryRecord.getBorrowedDate() != null)
            .map(libraryRecordMapper::toResponse)
            .toList();
  }

  @Transactional
  public List<LibraryRecordResponse> getAllRecord() {

    return libraryRecordRepository
            .findAllBy()
            .stream()
            .map(libraryRecordMapper::toResponse)
            .toList();
  }
}
