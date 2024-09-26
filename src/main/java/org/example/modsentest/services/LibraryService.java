package org.example.modsentest.services;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.example.modsentest.DTO.LibraryRecordDTO;
import org.example.modsentest.exceptions.NotFoundException;
import org.example.modsentest.models.Book;
import org.example.modsentest.models.LibraryRecord;
import org.example.modsentest.repositories.LibraryRecordRepo;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
public class LibraryService {
  private final LibraryRecordRepo libraryRecordRepo;
  private final ModelMapper modelMapper;

  @Transactional
  public void addBookToLibraryRecord(Book book) {

    LibraryRecord libraryRecord = new LibraryRecord(book);

    libraryRecordRepo.save(libraryRecord);
    modelMapper.map(libraryRecord, LibraryRecordDTO.class);
  }

  @Transactional
  public List<LibraryRecordDTO> getAllNoBorrowedBooks() {

    Stream<LibraryRecord> borrowedBookStream = libraryRecordRepo
            .streamByBorrowedDateNull();

    return borrowedBookStream
            .map((element) -> modelMapper.map(element, LibraryRecordDTO.class))
            .toList();
  }

  @Transactional
  public LibraryRecordDTO borrowBook(Long bookId, LocalDate borrowDate, LocalDate returnDate) {

    LibraryRecord libraryRecord = libraryRecordRepo.findByBookId(bookId)
            .orElseThrow(() -> new NotFoundException("Book not found by id: " + bookId));

    libraryRecord.setBorrowedDate(borrowDate);
    libraryRecord.setReturnedDate(returnDate);

    return modelMapper.map(libraryRecord, LibraryRecordDTO.class);
  }

  @Transactional
  public LibraryRecordDTO releaseBook(Long bookId) {

    LibraryRecord libraryRecord = libraryRecordRepo.findByBookId(bookId)
            .orElseThrow(() -> new NotFoundException("Book not found by id: " + bookId));

    libraryRecord.setBorrowedDate(null);
    libraryRecord.setReturnedDate(null);

    LibraryRecord addedLibraryRecord = libraryRecordRepo.save(libraryRecord);
    return modelMapper.map(addedLibraryRecord, LibraryRecordDTO.class);
  }

  @Transactional
  public List<LibraryRecordDTO> getAllBorrowedBooksRecord() {

    return libraryRecordRepo
            .streamAllBy()
            .filter(libraryRecord -> libraryRecord.getBorrowedDate() != null)
            .map((element) -> modelMapper.map(element, LibraryRecordDTO.class))
            .toList();
  }

  @Transactional
  public List<LibraryRecordDTO> getAllRecord(){

    return libraryRecordRepo
            .streamAllBy()
            .map((element) -> modelMapper.map(element, LibraryRecordDTO.class))
            .toList();
  }
}
