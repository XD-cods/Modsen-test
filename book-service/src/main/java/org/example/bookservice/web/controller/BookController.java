package org.example.bookservice.web.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.example.bookservice.service.BookService;
import org.example.bookservice.web.request.BookRequest;
import org.example.bookservice.web.response.BookResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/book")
@RequiredArgsConstructor
public class BookController {
  private final BookService bookService;

  @Operation(summary = "Получить список всех книг", description = "Возвращает список книг с необязательным" +
                                                                  " фильтром по префиксу заголовка")
  @ApiResponses(value = {
          @ApiResponse(responseCode = "200", description = "Список книг получен"),
          @ApiResponse(responseCode = "404", description = "Книги не найдены"),
          @ApiResponse(responseCode = "400", description = "Ошибка в запросе")
  })
  @GetMapping
  public ResponseEntity<List<BookResponse>> getBooks(@Parameter(description = "Префикс для фильтрации " +
                                                                              "по заголовку книги")
                                                     Optional<String> optionalPrefixTitle) {
    return ResponseEntity.ok(bookService.getAllBooks(optionalPrefixTitle));
  }

  @Operation(summary = "Получить книгу по ID", description = "Возвращает книгу по её идентификатору")
  @ApiResponses(value = {
          @ApiResponse(responseCode = "200", description = "Книга найдена"),
          @ApiResponse(responseCode = "404", description = "Книга не найдена"),
          @ApiResponse(responseCode = "400", description = "Ошибка в запросе")
  })
  @GetMapping("/{id}")
  public ResponseEntity<BookResponse> getBookById(@Parameter(description = "ID книги", required = true)
                                                  @PathVariable Long id) {
    return ResponseEntity.ok(bookService.getBookById(id));
  }

  @Operation(summary = "Создать новую книгу", description = "Создает новую книгу с указанными данными")
  @ApiResponses(value = {
          @ApiResponse(responseCode = "200", description = "Книга создана"),
          @ApiResponse(responseCode = "400", description = "Ошибка в запросе")
  })
  @PostMapping
  public ResponseEntity<BookResponse> createBook(@RequestBody BookRequest bookRequest) {
    return ResponseEntity.ok(bookService.addBook(bookRequest));
  }

  @Operation(summary = "Удалить книгу по ID", description = "Удаляет книгу по её идентификатору")
  @ApiResponses(value = {
          @ApiResponse(responseCode = "200", description = "Книга удалена"),
          @ApiResponse(responseCode = "404", description = "Книга не найдена"),
          @ApiResponse(responseCode = "400", description = "Ошибка в запросе")
  })
  @DeleteMapping("/{id}")
  public ResponseEntity<Boolean> deleteBookById(@Parameter(description = "ID книги", required = true)
                                                @PathVariable Long id) {
    return ResponseEntity.ok(bookService.deleteBook(id));
  }

  @Operation(summary = "Обновить книгу по ID", description = "Обновляет данные книги по её идентификатору")
  @ApiResponses(value = {
          @ApiResponse(responseCode = "200", description = "Книга обновлена"),
          @ApiResponse(responseCode = "404", description = "Книга не найдена"),
          @ApiResponse(responseCode = "400", description = "Ошибка в запросе")
  })
  @PutMapping("/{id}")
  public ResponseEntity<BookResponse> updateBookById(@Parameter(description = "ID книги", required = true)
                                                     @PathVariable Long id,
                                                     @RequestBody BookRequest bookRequest) {
    return ResponseEntity.ok(bookService.updateBook(id, bookRequest));
  }
}
