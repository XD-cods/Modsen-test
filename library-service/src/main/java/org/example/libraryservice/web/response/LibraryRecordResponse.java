package org.example.libraryservice.web.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LibraryRecordResponse {

  @JsonProperty("book_id")
  private Long bookId;

  @JsonProperty("borrowed_date")
  private LocalDate borrowedDate;

  @JsonProperty("returned_date")
  private LocalDate returnedDate;

}
