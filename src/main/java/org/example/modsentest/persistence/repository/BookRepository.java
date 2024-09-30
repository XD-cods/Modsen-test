package org.example.modsentest.persistence.repository;

import org.example.modsentest.persistence.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {
  List<Book> findAllBy();

  List<Book> findAllByTitleStartsWithIgnoreCase(String prefix);
}
