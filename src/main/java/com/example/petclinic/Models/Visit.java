package com.example.petclinic.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class Visit {
    @Id
    private Long id;

    @ManyToOne
    private Pet pet;

    private String visitDate;

    // Getters and Setters
}
