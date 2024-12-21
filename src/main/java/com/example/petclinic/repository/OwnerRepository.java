package com.yourproject.repository;

import com.yourproject.model.Owner;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OwnerRepository extends JpaRepository<Owner, Long> {
    // You can add custom queries here if needed, but no changes necessary for the firstName field.
}
