package org.example.modsentest.services;


import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.example.modsentest.DTO.BookDTO;
import org.example.modsentest.exceptions.NotFoundException;
import org.example.modsentest.models.Book;
import org.example.modsentest.repositories.BookRepo;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
public class BookService {
  private final BookRepo bookRepo;
  private final ModelMapper modelMapper;

  @Transactional
  public List<BookDTO> getAllBooks(Optional<String> optionalPrefixName) {

    optionalPrefixName = optionalPrefixName.filter(prefixName -> !prefixName.trim().isEmpty());

    Stream<Book> bookStream = optionalPrefixName
            .map(bookRepo::streamAllByTitleStartsWithIgnoreCase)
            .orElseGet(bookRepo::streamAllBy);

    return bookStream
            .map((book) -> modelMapper.map(book, BookDTO.class))
            .toList();
  }

  @Transactional
  public BookDTO getBookById(Long id) {

    return bookRepo
            .findById(id)
            .map(book -> modelMapper.map(book, BookDTO.class))
            .orElseThrow(() -> new NotFoundException("Book not found by id: " + id));
  }

  @Transactional
  public BookDTO addBook(BookDTO bookDTO) {

    Book book = modelMapper.map(bookDTO, Book.class);
    return modelMapper.map(bookRepo.save(book), BookDTO.class);
  }

  @Transactional
  public Boolean deleteBook(Long bookId) {

    bookRepo.deleteById(bookId);
    return true;
  }

  @Transactional
  public BookDTO updateBook(Long id, BookDTO bookDTO) {

    Book existingBook = bookRepo
            .findById(id)
            .orElseThrow(() -> new NotFoundException("Book not found by id: " + id));

    modelMapper.map(bookDTO, existingBook);
    if (!bookDTO.getGenre().isEmpty()) {
      existingBook.setGenre(bookDTO.getGenre());
    }

    bookRepo.save(existingBook);
    return modelMapper.map(existingBook, BookDTO.class);
  }

}
