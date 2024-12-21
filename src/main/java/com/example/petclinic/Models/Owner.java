package com.yourproject.model;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Owner {

    @Id
    private Long id;

    private String firstName; // New field added
    private String lastName;

    // Getters and Setters for all fields
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
}
