package com.example.petclinic.repository;

import com.example.petclinic.model.Owner;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OwnerRepository extends JpaRepository<Owner, Long> {
    // Custom query methods can be added here
}
