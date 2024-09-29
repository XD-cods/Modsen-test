package org.example.modsentest.persistence.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "library_record", schema = "library")
public class LibraryRecord {

  public LibraryRecord(Book book) {
    this.book = book;
    id = book.getId();
  }

  @Id
  Long id;

  @Builder.Default
  private LocalDate borrowedDate = LocalDate.now();

  private LocalDate returnedDate;

  @OneToOne(cascade = CascadeType.ALL)
  @JoinColumn(name = "book_id")
  @NotNull
  private Book book;

}
