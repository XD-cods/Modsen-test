package org.example.modsentest.service;


import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.example.modsentest.exception.NotFoundException;
import org.example.modsentest.persistence.entity.Book;
import org.example.modsentest.persistence.repository.BookRepository;
import org.example.modsentest.web.request.BookRequest;
import org.example.modsentest.web.response.BookResponse;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import static org.springframework.data.jpa.domain.AbstractPersistable_.id;

@Service
@RequiredArgsConstructor
public class BookService {
  private final BookRepository bookRepository;
  private final ModelMapper modelMapper;
  private final LibraryService libraryService;

  @Transactional
  public ResponseEntity<List<BookResponse>> getAllBooks(Optional<String> optionalPrefixName) {

    optionalPrefixName = optionalPrefixName.filter(prefixName -> !prefixName.trim().isEmpty());

    Stream<Book> bookStream = optionalPrefixName
            .map(bookRepository::streamAllByTitleStartsWithIgnoreCase)
            .orElseGet(bookRepository::streamAllBy);

    return ResponseEntity.ok(bookStream
            .map((book) -> modelMapper.map(book, BookResponse.class))
            .toList());
  }

  @Transactional
  public ResponseEntity<BookResponse> getBookById(Long id) {

    return ResponseEntity.ok(bookRepository
            .findById(id)
            .map(book -> modelMapper.map(book, BookResponse.class))
            .orElseThrow(() -> new NotFoundException("Book not found by id: " + id)));
  }

  @Transactional
  public ResponseEntity<BookResponse> addBook(BookRequest BookResponse) {

    Book book = modelMapper.map(BookResponse, Book.class);
    book = bookRepository.save(book);
    libraryService.addBookToLibraryRecord(book);
    return ResponseEntity.ok(modelMapper.map(bookRepository.save(book), BookResponse.class));
  }

  @Transactional
  public ResponseEntity<Boolean> deleteBook(Long bookId) {

    bookRepository.findById(bookId)
            .orElseThrow(()-> new NotFoundException("Book not found by id: " + id));

    bookRepository.deleteById(bookId);
    return ResponseEntity.ok(true);
  }

  @Transactional
  public ResponseEntity<BookResponse> updateBook(Long id, BookRequest BookResponse) {

    Book existingBook = bookRepository
            .findById(id)
            .orElseThrow(() -> new NotFoundException("Book not found by id: " + id));

    modelMapper.map(BookResponse, existingBook);
    if (!BookResponse.getGenre().isEmpty()) {
      existingBook.setGenre(BookResponse.getGenre());
    }

    bookRepository.save(existingBook);
    return ResponseEntity.ok(modelMapper.map(existingBook, BookResponse.class));
  }

}
