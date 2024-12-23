package org.example.bookservice.service;


import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.example.bookservice.exception.BadRequestException;
import org.example.bookservice.exception.NotFoundException;
import org.example.bookservice.persistence.entity.Book;
import org.example.bookservice.persistence.entity.Genre;
import org.example.bookservice.persistence.repository.BookRepository;
import org.example.bookservice.util.feign.LibraryServiceClient;
import org.example.bookservice.util.mapper.BookMapper;
import org.example.bookservice.web.request.BookRequest;
import org.example.bookservice.web.response.BookResponse;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
public class BookService {
  private final BookRepository bookRepository;
  private final BookMapper bookMapper;
  private final LibraryServiceClient libraryServiceClient;
  private static final String NOT_FOUND_BOOK_MESSAGE = "Book not found by id: %d";
  private static final String EXIST_BOOK_BY_ISBN_MESSAGE = "Book already exist by isbn: %s";

  @Transactional
  public List<BookResponse> getAllBooks(Optional<String> optionalPrefixName) {

    optionalPrefixName = optionalPrefixName.filter(prefixName -> !prefixName.trim().isEmpty());

    Stream<Book> bookStream = optionalPrefixName
            .map(bookRepository::findAllByTitleStartsWithIgnoreCase)
            .orElseGet(bookRepository::findAllBy)
            .stream();

    return bookStream
            .map(bookMapper::toResponse)
            .toList();
  }

  @Transactional
  public BookResponse getBookById(Long id) {

    return bookRepository
            .findById(id)
            .map(bookMapper::toResponse)
            .orElseThrow(() -> new NotFoundException(String.format(NOT_FOUND_BOOK_MESSAGE, id)));
  }

  public BookResponse addBook(BookRequest bookRequest) {

    if (bookRepository.existsBookByISBN(bookRequest.getISBN())) {
      throw new BadRequestException(String.format(EXIST_BOOK_BY_ISBN_MESSAGE, bookRequest.getISBN()));
    }

    Book book = bookMapper.requestToEntity(bookRequest);
    book = bookRepository.save(book);
    bookRequest = bookMapper.toRequest(book);

    libraryServiceClient.addLibraryRecord(bookRequest, book.getId());
    return bookMapper.toResponse(book);
  }

  @Transactional
  public Boolean deleteBook(Long id) {

    bookRepository.findById(id)
            .orElseThrow(() -> new NotFoundException(String.format(NOT_FOUND_BOOK_MESSAGE, id)));

    bookRepository.deleteById(id);
    return true;
  }

  @Transactional
  public BookResponse updateBook(Long id, BookRequest bookRequest) {

    if (bookRepository.existsBookByISBN(bookRequest.getISBN())) {
      throw new BadRequestException(String.format(EXIST_BOOK_BY_ISBN_MESSAGE, bookRequest.getISBN()));
    }

    Book existingBook = bookRepository
            .findById(id)
            .orElseThrow(() -> new NotFoundException(String.format(NOT_FOUND_BOOK_MESSAGE, id)));

    bookMapper.updateBookFromRequest(bookRequest, existingBook);
    List<Genre> bookRequestGenre = bookRequest.getGenre();

    if (bookRequestGenre != null) {
      existingBook.setGenre(bookRequestGenre);
    }

    bookRepository.save(existingBook);
    return bookMapper.toResponse(existingBook);
  }

}
