package com.example.petclinic.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.List;

@Entity
public class Owner {
    @Id
    private Long id;
    private String firstName;
    private String lastName;
    
    @OneToMany
    private List<Pet> pets;

    // Getters and Setters
}
