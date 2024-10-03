package org.example.bookservice.persistence.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
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

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  Long id;

  private LocalDate borrowedDate;

  private LocalDate returnedDate;

  @OneToOne(cascade = CascadeType.REMOVE)
  @JoinColumn(name = "book_id")
  @NotNull
  private Book book;

}
