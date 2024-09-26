package org.example.modsentest.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.example.modsentest.DTO.BookDTO;
import org.example.modsentest.services.BookService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("api/book")
@RequiredArgsConstructor
public class BookController {
  private final BookService bookService;

  @Operation(summary = "Получить список всех книг", description = "Возвращает список книг с необязательным фильтром по префиксу заголовка")
  @ApiResponses(value = {
          @ApiResponse(responseCode = "200", description = "Список книг получен"),
          @ApiResponse(responseCode = "404", description = "Книги не найдены"),
          @ApiResponse(responseCode = "400", description = "Ошибка в запросе")
  })
  @GetMapping
  public List<BookDTO> getBooks(@Parameter(description = "Префикс для фильтрации по заголовку книги") Optional<String> optionalPrefixTitle) {
    return bookService.getAllBooks(optionalPrefixTitle);
  }

  @Operation(summary = "Получить книгу по ID", description = "Возвращает книгу по её идентификатору")
  @ApiResponses(value = {
          @ApiResponse(responseCode = "200", description = "Книга найдена"),
          @ApiResponse(responseCode = "404", description = "Книга не найдена"),
          @ApiResponse(responseCode = "400", description = "Ошибка в запросе")
  })
  @GetMapping("/{id}")
  public BookDTO getBookById(@Parameter(description = "ID книги", required = true) @PathVariable Long id) {
    return bookService.getBookById(id);
  }

  @Operation(summary = "Создать новую книгу", description = "Создает новую книгу с указанными данными")
  @ApiResponses(value = {
          @ApiResponse(responseCode = "200", description = "Книга создана"),
          @ApiResponse(responseCode = "400", description = "Ошибка в запросе")
  })
  @PutMapping
  public BookDTO createBook(@RequestBody BookDTO bookDTO) {
    return bookService.addBook(bookDTO);
  }

  @Operation(summary = "Удалить книгу по ID", description = "Удаляет книгу по её идентификатору")
  @ApiResponses(value = {
          @ApiResponse(responseCode = "200", description = "Книга удалена"),
          @ApiResponse(responseCode = "404", description = "Книга не найдена"),
          @ApiResponse(responseCode = "400", description = "Ошибка в запросе")
  })
  @DeleteMapping("/{id}")
  public Boolean deleteBookById(@Parameter(description = "ID книги", required = true) @PathVariable Long id) {
    return bookService.deleteBook(id);
  }

  @Operation(summary = "Обновить книгу по ID", description = "Обновляет данные книги по её идентификатору")
  @ApiResponses(value = {
          @ApiResponse(responseCode = "200", description = "Книга обновлена"),
          @ApiResponse(responseCode = "404", description = "Книга не найдена"),
          @ApiResponse(responseCode = "400", description = "Ошибка в запросе")
  })
  @PatchMapping("/{id}")
  public BookDTO updateBookById(@Parameter(description = "ID книги", required = true) @PathVariable Long id, @RequestBody BookDTO bookDTO) {
    return bookService.updateBook(id, bookDTO);
  }
}
