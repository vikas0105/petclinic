package com.example.petclinic.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class SearchController {

    @GetMapping("/search")
    public String search(Model model) {
        // Logic to handle search functionality
        return "search";  // Will render search.html
    }
}
