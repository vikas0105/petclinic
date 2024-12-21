package com.example.petclinic.controller;

import com.example.petclinic.model.Pet;
import com.example.petclinic.repository.PetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class PetController {

    @Autowired
    private PetRepository petRepository;

    @GetMapping("/pets")
    public String listPets(Model model) {
        model.addAttribute("pets", petRepository.findAll());
        return "pets";
    }

    @PostMapping("/pets/add")
    public String addPet(Pet pet) {
        petRepository.save(pet);
        return "redirect:/pets";
    }
}
