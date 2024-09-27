package org.example.modsentest.persistence.repository;

import org.example.modsentest.persistence.entity.LibraryRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.stream.Stream;

@Repository
public interface LibraryRecordRepository extends JpaRepository<LibraryRecord, Long> {

  Stream<LibraryRecord> streamAllBy();

  Stream<LibraryRecord> streamByBorrowedDateNull();

  Optional<LibraryRecord> findByBookId(Long bookId);
}
