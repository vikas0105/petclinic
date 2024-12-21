package com.example.petclinic.controller;

import com.example.petclinic.model.Owner;
import com.example.petclinic.model.Pet;
import com.example.petclinic.repository.OwnerRepository;
import com.example.petclinic.repository.PetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/search")
public class SearchController {

    @Autowired
    private PetRepository petRepository;

    @Autowired
    private OwnerRepository ownerRepository;

    @GetMapping("/pets")
    public List<Pet> searchPets(@RequestParam String name) {
        return petRepository.findByNameContaining(name);
    }

    @GetMapping("/owners")
    public List<Owner> searchOwners(@RequestParam String firstName) {
        return ownerRepository.findByFirstNameContaining(firstName);
    }
}
