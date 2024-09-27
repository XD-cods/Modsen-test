package org.example.modsentest.persistence.repository;

import org.example.modsentest.persistence.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.stream.Stream;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {
  Stream<Book> streamAllBy();

  Stream<Book> streamAllByTitleStartsWithIgnoreCase(String prefix);
}
