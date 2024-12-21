package com.example.petclinic.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PetController {

    @GetMapping("/pets")
    public String getAllPets(Model model) {
        // Logic to retrieve and display pets
        // Example: model.addAttribute("pets", petService.findAll());
        return "pets";  // Will render pets.html
    }
}
