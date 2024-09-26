package org.example.modsentest.DTO;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LibraryRecordDTO {

  @JsonProperty("book_id")
  private Long bookId;

  @JsonProperty("borrowed_date")
  private LocalDate borrowedDate;

  @JsonProperty("returned_date")
  private LocalDate returnedDate;

}
