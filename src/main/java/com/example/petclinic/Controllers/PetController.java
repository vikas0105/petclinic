package com.example.petclinic.controller;

import com.example.petclinic.model.Pet;
import com.example.petclinic.repository.PetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/pets")
public class PetController {

    @Autowired
    private PetRepository petRepository;

    @GetMapping
    public String listPets(Model model) {
        model.addAttribute("pets", petRepository.findAll());
        return "pets";
    }

    @GetMapping("/add")
    public String showAddPetForm(Model model) {
        model.addAttribute("pet", new Pet());
        return "addPet";
    }

    @PostMapping("/add")
    public String addPet(Pet pet) {
        petRepository.save(pet);
        return "redirect:/pets";
    }

    @GetMapping("/{id}")
    public String viewPetDetails(@PathVariable Long id, Model model) {
        Pet pet = petRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid pet Id:" + id));
        model.addAttribute("pet", pet);
        return "petDetails";
    }

    @GetMapping("/search")
    public String searchPets(@RequestParam("search") String search, Model model) {
        model.addAttribute("pets", petRepository.findByNameContaining(search));
        return "pets";
    }
}
