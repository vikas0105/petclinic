package com.example.petclinic.controller;

import com.example.petclinic.model.Owner;
import com.example.petclinic.repository.OwnerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/owners")
public class OwnerController {

    @Autowired
    private OwnerRepository ownerRepository;

    @GetMapping
    public String listOwners(Model model) {
        model.addAttribute("owners", ownerRepository.findAll());
        return "owners";
    }

    @GetMapping("/add")
    public String showAddOwnerForm(Model model) {
        model.addAttribute("owner", new Owner());
        return "addOwner";
    }

    @PostMapping("/add")
    public String addOwner(Owner owner) {
        ownerRepository.save(owner);
        return "redirect:/owners";
    }
}
