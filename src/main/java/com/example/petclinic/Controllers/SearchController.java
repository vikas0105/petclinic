package com.example.petclinic.controller;

import com.example.petclinic.repository.PetRepository;
import com.example.petclinic.repository.OwnerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class SearchController {

    @Autowired
    private PetRepository petRepository;

    @Autowired
    private OwnerRepository ownerRepository;

    @GetMapping("/search")
    public String search(@RequestParam("query") String query, Model model) {
        model.addAttribute("pets", petRepository.findByNameContaining(query));
        model.addAttribute("owners", ownerRepository.findByFirstNameContaining(query));
        return "searchResults";
    }
}
