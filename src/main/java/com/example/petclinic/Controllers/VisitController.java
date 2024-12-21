package com.example.petclinic.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class VisitController {

    @GetMapping("/visits")
    public String getAllVisits(Model model) {
        // Logic to retrieve and display visits
        return "visits";  // Will render visits.html
    }
}
