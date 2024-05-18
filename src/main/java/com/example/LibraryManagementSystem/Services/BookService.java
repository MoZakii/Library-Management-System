package com.example.LibraryManagementSystem.Services;

import com.example.LibraryManagementSystem.Exceptions.BookInUseException;
import com.example.LibraryManagementSystem.Exceptions.BookNotFoundException;
import com.example.LibraryManagementSystem.Models.Book;
import com.example.LibraryManagementSystem.Repositories.BookRepository;
import com.example.LibraryManagementSystem.Repositories.BorrowingRecordRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BookService {

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private BorrowingRecordRepository borrowingRecordRepository;

    @Cacheable("books")
    public List<Book> getAllBooks(){ return bookRepository.findAll(); }

    public List<Book> getAvailableBooks()
    {
        return bookRepository.findByAvailableTrue();
    }

    @Cacheable("books")
    public Book getBookById(Long id) throws BookNotFoundException
    {
        Optional<Book> book = bookRepository.findById(id);
        return book.orElseThrow(() -> new BookNotFoundException("Book with ID " + id + " not found"));
    }

    @CacheEvict(value = "books", allEntries = true)
    public Book createBook(Book book){
        book.setAvailable(true);
        return bookRepository.save(book);
    }

    @CacheEvict(value = "books", allEntries = true)
    public Book updateBook(Long id, Book updatedBook) throws BookNotFoundException
    {
        Book existingBook = getBookById(id);
        existingBook.setTitle(updatedBook.getTitle());
        existingBook.setAuthor(updatedBook.getAuthor());
        existingBook.setISBN(updatedBook.getISBN());
        existingBook.setPublicationYear(updatedBook.getPublicationYear());

        return bookRepository.save(existingBook);
    }

    @CacheEvict(value = "books", allEntries = true)
    public void deleteBook(Long id) throws BookNotFoundException, BookInUseException{
        Book book = getBookById(id);
        if(borrowingRecordRepository.existsByBook_IdAndReturnedDateIsNull(id)){
            throw new BookInUseException("Book with ID " + id + " cannot be deleted as it has ongoing borrowings");
        }
        bookRepository.deleteById(id);
    }
}
