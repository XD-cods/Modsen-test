package org.example.bookservice.web.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.bookservice.persistence.entity.Genre;

import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BookRequest {

  private String title;

  @JsonProperty(value = "isbn")
  private String ISBN;

  @Builder.Default
  private List<Genre> genre = new ArrayList<>();

  private String description;

  private String author;
}
