// CustomUserDetailsService.java
package com.alexandra.clinica.service;

import com.alexandra.clinica.entity.Medic;
import com.alexandra.clinica.repository.MedicRepository; // Presupunem că ai acest repo
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final MedicRepository medicRepository;

    public CustomUserDetailsService(MedicRepository medicRepository) {
        this.medicRepository = medicRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        
        return medicRepository.findByUsername(username) 
                .orElseThrow(() -> new UsernameNotFoundException("Utilizator negăsit: " + username));
    }
}