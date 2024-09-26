package org.example.modsentest.controller;

import lombok.RequiredArgsConstructor;
import org.example.modsentest.DTO.BookDTO;
import org.example.modsentest.services.BookService;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("api/book")
@RequiredArgsConstructor
public class BookController {
  private final BookService bookService;

  @GetMapping
  public List<BookDTO> getBooks(Optional<String> optionalPrefixTitle) {
    return bookService.getAllBooks(optionalPrefixTitle);
  }

  @GetMapping("/{id}")
  public BookDTO getBookById(@PathVariable Long id) {
    return bookService.getBookById(id);
  }

  @PostMapping
  public BookDTO createBook(@RequestBody BookDTO bookDTO) {
    return bookService.addBook(bookDTO);
  }

  @DeleteMapping("/{id}")
  public Boolean deleteBookById(@PathVariable Long id) {
    return bookService.deleteBook(id);
  }

  @PatchMapping("/{id}")
  public BookDTO updateBookById(@PathVariable Long id, @RequestBody BookDTO bookDTO) {
    return bookService.updateBook(id, bookDTO);
  }
}
