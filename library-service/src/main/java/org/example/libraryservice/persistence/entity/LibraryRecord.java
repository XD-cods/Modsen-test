package org.example.libraryservice.persistence.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
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

  private Long bookId;

}
