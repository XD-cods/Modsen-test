package org.example.modsentest.DTO;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.modsentest.models.Genre;

import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BookDTO {
  private Long id;

  @JsonProperty(value = "isbn")
  private String ISBN;

  private String title;

  @Builder.Default
  private List<Genre> genre = new ArrayList<>();

  private String description;

  private String author;
}
