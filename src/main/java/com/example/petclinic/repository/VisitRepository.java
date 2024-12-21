package com.example.petclinic.repository;

import com.example.petclinic.model.Visit;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VisitRepository extends JpaRepository<Visit, Long> {
    // You can define custom queries here if needed
}
