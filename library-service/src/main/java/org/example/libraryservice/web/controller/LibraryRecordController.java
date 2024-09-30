package org.example.libraryservice.web.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.example.libraryservice.service.LibraryService;
import org.example.libraryservice.web.response.LibraryRecordResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/record")
@RequiredArgsConstructor
public class LibraryRecordController {
  private final LibraryService libraryService;

  @Operation(summary = "Получить список всех записей",
          description = "Возвращает список записей, которые есть в библиотеке")
  @ApiResponses(value = {
          @ApiResponse(responseCode = "200", description = "Список всех записей"),
          @ApiResponse(responseCode = "404", description = "Записи не найдены"),
          @ApiResponse(responseCode = "400", description = "Ошибка в запросе")
  })
  @GetMapping
  public ResponseEntity<List<LibraryRecordResponse>> getAllRecords() {
    return libraryService.getAllRecord();
  }

  @Operation(summary = "Получить список книг, которые не находятся на руках",
          description = "Возвращает список книг, которые доступны в библиотеке")
  @ApiResponses(value = {
          @ApiResponse(responseCode = "200", description = "Список доступных книг получен"),
          @ApiResponse(responseCode = "404", description = "Книги не найдены"),
          @ApiResponse(responseCode = "400", description = "Ошибка в запросе")
  })
  @GetMapping("/release")
  public ResponseEntity<List<LibraryRecordResponse>> getNoBorrowedBooks() {
    return libraryService.getAllNoBorrowedBooks();
  }

  @Operation(summary = "Получить список книг, которые находятся на руках",
          description = "Возвращает список книг, которые являются забронированными в библиотеке")
  @ApiResponses(value = {
          @ApiResponse(responseCode = "200", description = "Список доступных книг получен"),
          @ApiResponse(responseCode = "404", description = "Книги не найдены"),
          @ApiResponse(responseCode = "400", description = "Ошибка в запросе")
  })
  @GetMapping("/borrow")
  public ResponseEntity<List<LibraryRecordResponse>> getAllBorrowedBooksRecord() {
    return libraryService.getAllBorrowedBooksRecord();
  }

  @Operation(summary = "Забронировать книгу", description = "Позволяет забронировать книгу по её ID, указав дату")
  @ApiResponses(value = {
          @ApiResponse(responseCode = "200", description = "Книга успешно забронирована"),
          @ApiResponse(responseCode = "404", description = "Книга не найдена"),
          @ApiResponse(responseCode = "400", description = "Ошибка в запросе")
  })
  @PatchMapping("/borrow/{bookId}")
  public ResponseEntity<LibraryRecordResponse> borrowBook(
          @Parameter(description = "ID книги", required = true) @PathVariable("bookId") Long bookId,
          @Parameter(description = "Дата взятия", required = true) @RequestParam("borrow_date") LocalDate borrowDate,
          @Parameter(description = "Дата возврата", required = true) @RequestParam("return_date") LocalDate returnDate) {
    return libraryService.borrowBook(bookId, borrowDate, returnDate);
  }

  @Operation(summary = "Освободить книгу", description = "Позволяет освободить книгу по её ID")
  @ApiResponses(value = {
          @ApiResponse(responseCode = "200", description = "Книга успешно освобождена"),
          @ApiResponse(responseCode = "404", description = "Книга не найдена"),
          @ApiResponse(responseCode = "400", description = "Ошибка в запросе")
  })
  @PatchMapping("/release/{bookId}")
  public ResponseEntity<LibraryRecordResponse> releaseBook(
          @Parameter(description = "ID книги", required = true) @PathVariable("bookId") Long bookId) {
    return libraryService.releaseBook(bookId);
  }
}
