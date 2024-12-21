package com.example.petclinic.Controllers;

import com.example.petclinic.model.Visit;
import com.example.petclinic.repository.PetRepository;
import com.example.petclinic.repository.VisitRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class VisitController {

    @Autowired
    private VisitRepository visitRepository;

    @Autowired
    private PetRepository petRepository;

    // This method initializes the form for creating a new visit, and sets the petId for the visit.
    @GetMapping("/visits/new")
    public String initCreationForm(@RequestParam("petId") Long petId, Model model) {
        Visit visit = new Visit();
        visit.setPetId(petId);  // Set the petId for the visit object
        model.addAttribute("visit", visit);
        return "createOrUpdateVisitForm";  // Make sure this HTML form exists
    }

    // This method processes the form submission and saves the visit.
    @PostMapping("/visits/new")
    public String processCreationForm(Visit visit) {
        visitRepository.save(visit);
        return "redirect:/pets/{petId}";  // Redirect after saving the visit
    }
}
