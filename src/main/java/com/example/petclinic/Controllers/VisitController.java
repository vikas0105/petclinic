package com.example.petclinic.controller;

import com.example.petclinic.model.Visit;
import com.example.petclinic.repository.VisitRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class VisitController {

    @Autowired
    private VisitRepository visitRepository;

    @GetMapping("/pets/{petId}/visits/new")
    public String showAddVisitForm(@PathVariable Long petId, Model model) {
        Visit visit = new Visit();
        visit.setPetId(petId);  // Set the pet ID for the visit
        model.addAttribute("visit", visit);
        return "addVisit";
    }

    @PostMapping("/pets/{petId}/visits")
    public String addVisit(@PathVariable Long petId, @ModelAttribute Visit visit) {
        visit.setPetId(petId);
        visitRepository.save(visit);
        return "redirect:/pets/" + petId;
    }
}
