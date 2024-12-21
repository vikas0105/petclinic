package com.example.petclinic.model;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Pet {
    @Id
    private Long id;
    private String name;
    private String type;

    // Getters and Setters
}
