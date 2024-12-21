package com.example.petclinic;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.boot.orm.jpa.EntityScan;

@SpringBootApplication
@EnableJpaRepositories(basePackages = "com.example.petclinic.repository") // Ensures Spring scans for repositories
@EntityScan(basePackages = "com.example.petclinic.model") // Scans for entities in the model package
public class PetClinicApplication {
    public static void main(String[] args) {
        SpringApplication.run(PetClinicApplication.class, args);
    }
}
