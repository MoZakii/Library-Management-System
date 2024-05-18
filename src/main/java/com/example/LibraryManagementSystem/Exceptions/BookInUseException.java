package com.example.LibraryManagementSystem.Exceptions;

public class BookInUseException extends RuntimeException {

    public BookInUseException(String message) {
        super(message);
    }
}