package com.example.LibraryManagementSystem.Exceptions;

public class PatronInUseException extends RuntimeException {

    public PatronInUseException(String message) {
        super(message);
    }
}