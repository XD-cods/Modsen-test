package org.example.libraryservice.persistence.repository;

import org.example.libraryservice.persistence.entity.LibraryRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface LibraryRecordRepository extends JpaRepository<LibraryRecord, Long> {

  List<LibraryRecord> findAllBy();

  List<LibraryRecord> findByBorrowedDateNull();

  Optional<LibraryRecord> findByBookId(Long bookId);
}
