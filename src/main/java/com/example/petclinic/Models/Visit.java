package com.example.petclinic.model;

import javax.persistence.*;
import java.util.Date;

@Entity
public class Visit {

    @Id
    @GeneratedValue
    private Long id;  // Visit ID

    private Date date;
    private String description;

    @ManyToOne
    @JoinColumn(name = "pet_id")
    private Pet pet;  // Pet associated with this visit

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Pet getPet() {
        return pet;
    }

    public void setPet(Pet pet) {
        this.pet = pet;
    }

    // Add setPetId method to set the pet's id
    public void setPetId(Long petId) {
        if (this.pet == null) {
            this.pet = new Pet();  // Ensure pet is initialized if null
        }
        this.pet.setId(petId);  // Set the pet's ID
    }
}
