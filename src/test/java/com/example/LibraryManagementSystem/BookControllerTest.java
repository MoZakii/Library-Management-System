package com.example.LibraryManagementSystem;

import com.example.LibraryManagementSystem.Controllers.BookController;
import com.example.LibraryManagementSystem.Models.Book;
import com.example.LibraryManagementSystem.Repositories.BookRepository;
import com.example.LibraryManagementSystem.Services.BookService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
public class BookControllerTest {

    @Autowired
    private WebApplicationContext context;

    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;


    @Autowired
    private BookService bookService; // Inject BookService for interacting with the service

    @MockBean // Mock the repository to avoid database interaction during tests
    private BookRepository bookRepository;

    @Before // Setup before each test
    public void setup() {
        mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
    }
    @Test
    public void testAddBookSuccess() throws Exception {
        // Create a new book object
        Book newBook = new Book("Test Book", "Test Author", 2023, "1233");


        // Mock book service behavior to return the saved book
        Book savedBook = new Book("Test Book", "Test Author", 2023, "1233"); // Create expected saved book object
        Mockito.when(bookRepository.save(newBook)).thenReturn(savedBook);


        // Simulate a POST request to add the book
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/api/books")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(newBook)))
                .andExpect(status().isCreated())
                .andReturn();

        System.out.println("Saved Book: " + savedBook);
        // Assert that the response contains the expected book details
        String responseBody = result.getResponse().getContentAsString();
        Book responseBook = objectMapper.readValue(responseBody, Book.class);
        assertEquals("Test Book", responseBook.getTitle());
        assertEquals("Test Author", responseBook.getAuthor());
        assertEquals((Integer)2023, responseBook.getPublicationYear());
    }

    @Test
    public void testGetBookByIdSuccess() throws Exception {
        // Define a book ID
        Long bookId = 1L;

        // Mock book repository behavior to return a book for the ID
        Book expectedBook = new Book("Existing Book", "Existing Author", 2022, "123");
        Mockito.when(bookRepository.findById(bookId)).thenReturn(Optional.of(expectedBook));

        // Simulate a GET request to retrieve the book
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/api/books/{id}", bookId)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        // Assert that the response contains the expected book details
        String responseBody = result.getResponse().getContentAsString();
        Book responseBook = objectMapper.readValue(responseBody, Book.class);
        assertEquals(expectedBook.getId(), responseBook.getId());
        assertEquals(expectedBook.getTitle(), responseBook.getTitle());
        assertEquals(expectedBook.getAuthor(), responseBook.getAuthor());
        assertEquals(expectedBook.getPublicationYear(), responseBook.getPublicationYear());
    }

    @Test
    public void testGetBookByIdNotFound() throws Exception {
        // Define a non-existent book ID
        Long bookId = 10L;

        // Mock book repository behavior to return null
        Mockito.when(bookRepository.findById(bookId)).thenReturn(Optional.empty());

        // Simulate a GET request
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/api/books/{id}", bookId)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andReturn();

        // Assert that the response indicates not found
        String responseBody = result.getResponse().getContentAsString();
        assertTrue(responseBody.contains("not found"));
    }

    // Similar test methods for other functionalities (update, delete, etc.)

    private static String asJsonString(Object object) throws JsonProcessingException {
        return new ObjectMapper().writeValueAsString(object);
    }
}