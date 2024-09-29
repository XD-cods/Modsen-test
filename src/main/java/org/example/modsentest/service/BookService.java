package org.example.modsentest.service;


import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.example.modsentest.exception.NotFoundException;
import org.example.modsentest.persistence.entity.Book;
import org.example.modsentest.persistence.repository.BookRepository;
import org.example.modsentest.util.mapper.BookMapper;
import org.example.modsentest.web.request.BookRequest;
import org.example.modsentest.web.response.BookResponse;
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
  private final LibraryService libraryService;
  private final BookMapper bookMapper;

  @Transactional
  public ResponseEntity<List<BookResponse>> getAllBooks(Optional<String> optionalPrefixName) {

    optionalPrefixName = optionalPrefixName.filter(prefixName -> !prefixName.trim().isEmpty());

    Stream<Book> bookStream = optionalPrefixName
            .map(bookRepository::findAllByTitleStartsWithIgnoreCase)
            .orElseGet(bookRepository::findAllBy)
            .stream();

    return ResponseEntity.ok(bookStream
            .map(bookMapper::toResponse)
            .toList());
  }

  @Transactional
  public ResponseEntity<BookResponse> getBookById(Long id) {

    return ResponseEntity.ok(bookRepository
            .findById(id)
            .map(bookMapper::toResponse)
            .orElseThrow(() -> new NotFoundException("Book not found by id: " + id)));
  }

  @Transactional
  public ResponseEntity<BookResponse> addBook(BookRequest bookRequest) {

    Book book = bookMapper.requestToEntity(bookRequest);
    book = bookRepository.save(book);
    libraryService.addBookToLibraryRecord(book);
    return ResponseEntity.ok(bookMapper.toResponse(book));
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

    if (!BookResponse.getGenre().isEmpty()) {
      existingBook.setGenre(BookResponse.getGenre());
    }

    bookRepository.save(existingBook);
    return ResponseEntity.ok(bookMapper.toResponse(existingBook));
  }

}
