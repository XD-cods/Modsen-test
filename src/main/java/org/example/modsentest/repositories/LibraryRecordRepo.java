package org.example.modsentest.repositories;

import org.example.modsentest.models.LibraryRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.stream.Stream;

@Repository
public interface LibraryRecordRepo extends JpaRepository<LibraryRecord, Long> {

  Stream<LibraryRecord> streamAllBy();

  Stream<LibraryRecord> streamByBorrowedDateNull();

  Optional<LibraryRecord> findByBookId(Long bookId);
}
