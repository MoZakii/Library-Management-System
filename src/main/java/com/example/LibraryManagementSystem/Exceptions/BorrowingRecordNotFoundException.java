package com.example.LibraryManagementSystem.Exceptions;

public class BorrowingRecordNotFoundException extends RuntimeException {

    public BorrowingRecordNotFoundException(String message) {
        super(message);
    }
}