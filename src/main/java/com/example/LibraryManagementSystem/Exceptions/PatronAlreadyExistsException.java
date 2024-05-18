package com.example.LibraryManagementSystem.Exceptions;

public class PatronAlreadyExistsException extends RuntimeException{
    public PatronAlreadyExistsException(String message) {
        super(message);
    }
}
