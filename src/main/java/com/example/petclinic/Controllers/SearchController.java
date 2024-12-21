package com.example.petclinic.controller;

import com.example.petclinic.model.Owner;
import com.example.petclinic.repository.OwnerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/search")
public class SearchController {

    @Autowired
    private OwnerRepository ownerRepository;

    @GetMapping("/owners")
    public List<Owner> searchOwners(@RequestParam String lastName) {
        return ownerRepository.findByLastName(lastName);
    }
}
