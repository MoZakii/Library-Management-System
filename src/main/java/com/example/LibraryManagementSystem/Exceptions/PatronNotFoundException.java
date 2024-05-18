package com.example.LibraryManagementSystem.Exceptions;

public class PatronNotFoundException extends RuntimeException {

    public PatronNotFoundException(String message) {
        super(message);
    }
}