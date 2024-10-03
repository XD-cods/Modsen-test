package org.example.libraryservice.web.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.libraryservice.persistence.entity.Book;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LibraryRecordResponse {

  public LibraryRecordResponse(Long id, Book book, LocalDate borrowedDate, LocalDate returnedDate) {
    this.recordId = id;
    this.bookId = book.getId();
    this.borrowedDate = borrowedDate;
    this.returnedDate = returnedDate;
  }

  @JsonProperty("record_id")
  private Long recordId;

  @JsonProperty("book_id")
  private Long bookId;

  @JsonProperty("borrowed_date")
  private LocalDate borrowedDate;

  @JsonProperty("returned_date")
  private LocalDate returnedDate;

}
