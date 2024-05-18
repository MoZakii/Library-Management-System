package com.example.LibraryManagementSystem.Services;

import com.example.LibraryManagementSystem.Exceptions.PatronAlreadyExistsException;
import com.example.LibraryManagementSystem.Exceptions.PatronInUseException;
import com.example.LibraryManagementSystem.Exceptions.PatronNotFoundException;
import com.example.LibraryManagementSystem.Models.Patron;
import com.example.LibraryManagementSystem.Repositories.BorrowingRecordRepository;
import com.example.LibraryManagementSystem.Repositories.PatronRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PatronService {

    @Autowired
    private PatronRepository patronRepository;

    @Autowired
    private BorrowingRecordRepository borrowingRecordRepository;

    @Cacheable("patrons")
    public List<Patron> getAllPatrons()
    {
        return patronRepository.findAll();
    }

    @Cacheable("patrons")
    public Patron getPatronById(Long id) throws PatronNotFoundException
    {
        Optional<Patron> patron = patronRepository.findById(id);
        return patron.orElseThrow(() -> new PatronNotFoundException("Patron with ID " + id + " not found."));
    }

    @CacheEvict(value = "patrons", allEntries = true)
    public Patron createPatron(Patron patron) throws PatronAlreadyExistsException
    {
        if(patronRepository.findByEmail(patron.getEmail()).isEmpty())
            return patronRepository.save(patron);
        else
            throw new PatronAlreadyExistsException("This email: " + patron.getEmail() + " already exists.");
    }

    @CacheEvict(value = "patrons", allEntries = true)
    public Patron updatePatron(Long id, Patron updatedPatron) throws PatronNotFoundException
    {
        Patron existingPatron = getPatronById(id);
        existingPatron.setName(updatedPatron.getName());
        existingPatron.setEmail(updatedPatron.getEmail());
        existingPatron.setPhoneNumber(updatedPatron.getPhoneNumber());
        return patronRepository.save(existingPatron);
    }

    @CacheEvict(value = "patrons", allEntries = true)
    public void deletePatron(Long id) throws PatronNotFoundException, PatronInUseException
    {
        Patron patron = getPatronById(id);
        if (borrowingRecordRepository.existsByPatron_IdAndReturnedDateIsNull(id)) {
            throw new PatronInUseException("Patron with ID " + id + " cannot be deleted as they have ongoing borrowings");
        }
        patronRepository.deleteById(id);
    }

}
