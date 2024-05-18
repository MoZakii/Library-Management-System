package com.example.LibraryManagementSystem.Services;

import com.example.LibraryManagementSystem.Exceptions.BookNotFoundException;
import com.example.LibraryManagementSystem.Exceptions.BookUnavailableException;
import com.example.LibraryManagementSystem.Exceptions.BorrowingRecordNotFoundException;
import com.example.LibraryManagementSystem.Exceptions.PatronNotFoundException;
import com.example.LibraryManagementSystem.Models.Book;
import com.example.LibraryManagementSystem.Models.BorrowingRecord;
import com.example.LibraryManagementSystem.Models.Patron;
import com.example.LibraryManagementSystem.Repositories.BookRepository;
import com.example.LibraryManagementSystem.Repositories.BorrowingRecordRepository;
import com.example.LibraryManagementSystem.Repositories.PatronRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
public class BorrowingRecordService {
    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private PatronRepository patronRepository;

    @Autowired
    private BorrowingRecordRepository borrowingRecordRepository;

    @Transactional
    public BorrowingRecord borrowBook(Long bookId, Long patronId) throws BookNotFoundException, PatronNotFoundException, BookUnavailableException {

        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new BookNotFoundException("Book with ID " + bookId + " not found"));

        if (!book.getAvailable()) {
            throw new BookUnavailableException("Book with ID " + bookId + " is not currently available");
        }

        Patron patron = patronRepository.findById(patronId)
                .orElseThrow(() -> new PatronNotFoundException("Patron with ID " + patronId + " not found"));

        BorrowingRecord record = new BorrowingRecord();
        record.setBook(book);
        record.setPatron(patron);
        record.setBorrowedDate(LocalDateTime.now());

        book.setAvailable(false);
        bookRepository.save(book);
        return borrowingRecordRepository.save(record);
    }

    @Transactional
    public void returnBook(Long bookId, Long patronId) throws BorrowingRecordNotFoundException {
        BorrowingRecord record = borrowingRecordRepository.findByBook_IdAndPatron_IdAndReturnedDateIsNull(bookId, patronId)
                .orElseThrow(() -> new BorrowingRecordNotFoundException("Borrowing record not found for book " + bookId + " and patron " + patronId));

        record.setReturnedDate(LocalDateTime.now());
        borrowingRecordRepository.save(record);

        Book book = record.getBook();
        book.setAvailable(true); // Update book availability
        bookRepository.save(book);
    }
}
