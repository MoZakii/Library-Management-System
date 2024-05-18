package com.example.LibraryManagementSystem.Controllers;

import com.example.LibraryManagementSystem.Exceptions.BookInUseException;
import com.example.LibraryManagementSystem.Exceptions.BookNotFoundException;
import com.example.LibraryManagementSystem.Exceptions.BorrowingRecordNotFoundException;
import com.example.LibraryManagementSystem.Exceptions.PatronNotFoundException;
import com.example.LibraryManagementSystem.Models.BorrowingRecord;
import com.example.LibraryManagementSystem.Services.BorrowingRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/")
@Validated
public class BorrowingRecordController {
    @Autowired
    private BorrowingRecordService borrowingRecordService;

    @PostMapping("borrow/{bookId}/patron/{patronId}")
    public ResponseEntity<BorrowingRecord> borrowBook(@PathVariable Long patronId, @PathVariable Long bookId) throws BookNotFoundException, PatronNotFoundException, BookInUseException {
        BorrowingRecord borrowingRecord = borrowingRecordService.borrowBook(bookId, patronId);
        return ResponseEntity.status(HttpStatus.CREATED).body(borrowingRecord);
    }

    @PutMapping("return/{bookId}/patron/{patronId}")
    public ResponseEntity<Void> returnBook(@PathVariable Long patronId, @PathVariable Long bookId) throws BorrowingRecordNotFoundException {
        borrowingRecordService.returnBook(bookId, patronId);
        return ResponseEntity.noContent().build();
    }
}
