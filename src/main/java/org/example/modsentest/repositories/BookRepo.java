package org.example.modsentest.repositories;

import org.example.modsentest.models.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.stream.Stream;

@Repository
public interface BookRepo extends JpaRepository<Book, Long> {
  Stream<Book> streamAllBy();

  Stream<Book> streamAllByTitleStartsWithIgnoreCase(String prefix);
}
