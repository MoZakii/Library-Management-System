package com.example.LibraryManagementSystem.Controllers;

import com.example.LibraryManagementSystem.Exceptions.PatronAlreadyExistsException;
import com.example.LibraryManagementSystem.Exceptions.PatronInUseException;
import com.example.LibraryManagementSystem.Exceptions.PatronNotFoundException;
import com.example.LibraryManagementSystem.Models.Patron;
import com.example.LibraryManagementSystem.Services.PatronService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/patrons")
@Validated
public class PatronController {

    @Autowired
    private PatronService patronService;

    @GetMapping
    public ResponseEntity<List<Patron>> getAllPatrons(){
        return ResponseEntity.ok(patronService.getAllPatrons());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Patron> getPatronById(@PathVariable Long id) throws PatronNotFoundException {
        Patron patron = patronService.getPatronById(id);
        return ResponseEntity.ok(patron);
    }

    @PostMapping
    public ResponseEntity<Patron> createPatron(@RequestBody Patron patron) throws PatronAlreadyExistsException {
        patron = patronService.createPatron(patron);
        return ResponseEntity.status(HttpStatus.CREATED).body(patron);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Patron> updatePatron(@PathVariable Long id, @RequestBody Patron patron) throws PatronNotFoundException {
        patron.setId(id); // Ensure ID matches path variable
        patron = patronService.updatePatron(id, patron);
        return ResponseEntity.ok(patron);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletePatron(@PathVariable Long id) throws PatronNotFoundException, PatronInUseException {
        patronService.deletePatron(id);
    }
}
