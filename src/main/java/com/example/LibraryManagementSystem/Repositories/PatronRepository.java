package com.example.LibraryManagementSystem.Repositories;

import com.example.LibraryManagementSystem.Models.Patron;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PatronRepository extends JpaRepository<Patron, Long> {

    List<Patron> findByEmail(String email);
}
