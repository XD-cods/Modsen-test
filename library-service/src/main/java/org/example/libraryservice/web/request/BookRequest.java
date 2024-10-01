package org.example.libraryservice.web.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.libraryservice.persistence.entity.Genre;

import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BookRequest {

  @JsonProperty(value = "isbn")
  private String ISBN;

  private String title;

  @Builder.Default
  private List<Genre> genre = new ArrayList<>();

  private String description;

  private String author;
}
