package org.example.bookservice.persistence.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "book", schema = "library")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Book {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "book_id")
  private Long id;

  @Column(unique = true)
  private String ISBN;

  private String title;

  private List<Genre> genre = null;

  private String description;

  private String author;

  @OneToOne(mappedBy = "book", cascade = CascadeType.REMOVE, orphanRemoval = true)
  private LibraryRecord libraryRecord;
}
