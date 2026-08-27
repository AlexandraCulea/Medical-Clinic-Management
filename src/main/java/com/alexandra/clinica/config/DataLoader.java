package com.alexandra.clinica.config; 

import com.alexandra.clinica.entity.Medic;
import com.alexandra.clinica.service.MedicService;
import com.alexandra.clinica.repository.MedicRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class DataLoader {

    @Bean
    public CommandLineRunner initDatabase(
        MedicRepository medicRepository, 
        PasswordEncoder passwordEncoder,
        MedicService medicService) {
        
        return args -> {
            // admin
            if (medicRepository.findByUsername("admin_alex").isEmpty()) {
                Medic admin = Medic.builder()
                        .username("admin_alex")
                        .password(passwordEncoder.encode("supersecret")) 
                        .role("ADMIN")
                        .numeMedic("Admin")
                        .prenumeMedic("Super")
                        .specializare("Administrare")
                        .build();
                medicRepository.save(admin);
                System.out.println("--- ADMIN user created: admin_alex ---");
            }

            //  user(medic)
            if (medicRepository.findByUsername("dr_popescu").isEmpty()) {
                Medic medic = Medic.builder()
                        .username("dr_popescu")
                        .password(passwordEncoder.encode("secret123")) 
                        .role("MEDIC")
                        .numeMedic("Popescu")
                        .prenumeMedic("Marius")
                        .specializare("Pediatrie")
                        .build();
                medicRepository.save(medic);
                System.out.println("--- MEDIC user created: dr_popescu ---");
            }

            medicService.initializeMissingSecurityFields();
        };
    }
}