package com.example.petclinic.controller;

import com.example.petclinic.model.Pet;
import com.example.petclinic.repository.PetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class PetController {

    @Autowired
    private PetRepository petRepository;

    // Show all pets
    @GetMapping("/pets")
    public String listPets(Model model) {
        model.addAttribute("pets", petRepository.findAll());
        return "pets";
    }

    // Add a new pet
    @GetMapping("/pets/new")
    public String showAddPetForm(Model model) {
        model.addAttribute("pet", new Pet());
        return "addPet";
    }

    @PostMapping("/pets")
    public String addPet(@ModelAttribute Pet pet) {
        petRepository.save(pet);
        return "redirect:/pets";
    }

    // View pet details
    @GetMapping("/pets/{id}")
    public String viewPet(@PathVariable Long id, Model model) {
        Pet pet = petRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid pet Id:" + id));
        model.addAttribute("pet", pet);
        return "petDetails";
    }
}
