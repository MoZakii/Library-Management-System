package com.example.LibraryManagementSystem.Repositories;

import com.example.LibraryManagementSystem.Models.BorrowingRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BorrowingRecordRepository extends JpaRepository<BorrowingRecord, Long> {

    public Boolean existsByPatron_IdAndReturnedDateIsNull(Long patronId);

    public Boolean existsByBook_IdAndReturnedDateIsNull(Long bookId);

    public Optional<BorrowingRecord> findByBook_IdAndPatron_IdAndReturnedDateIsNull(Long bookId, Long patronId);
}
