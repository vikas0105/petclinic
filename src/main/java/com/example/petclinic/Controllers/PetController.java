package com.example.petclinic.controller;

import com.example.petclinic.model.Pet;
import com.example.petclinic.repository.PetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/pets")
public class PetController {

    @Autowired
    private PetRepository petRepository;

    @GetMapping("/search")
    public List<Pet> searchPets(@RequestParam String name) {
        return petRepository.findByNameContaining(name);
    }
}
