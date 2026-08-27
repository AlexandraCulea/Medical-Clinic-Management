package com.alexandra.clinica.controller.web;

import com.alexandra.clinica.entity.Pacient;
import com.alexandra.clinica.entity.Consultatie;
import com.alexandra.clinica.service.PacientService;
import com.alexandra.clinica.service.ConsultatieService;
import com.alexandra.clinica.service.MedicamentService; // Necesar pentru formular
import com.alexandra.clinica.controller.dto.CreateConsultatieRequest; // DTO-ul tău de request
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException; // Pentru a prinde excepția de securitate
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import org.springframework.security.core.Authentication;

@Controller
@RequestMapping("/consultatii")
public class ConsultatieWebController {

    @Autowired
    private ConsultatieService consultatieService;
    @Autowired
    private PacientService pacientService;
    @Autowired
    private MedicamentService medicamentService; 

    
    @GetMapping
    public String listConsultatiiForPacient(@RequestParam Long pacientId, Model model) {
        
        
        Pacient pacient = pacientService.getPacient(pacientId); 
        
       
        List<Consultatie> consultatii = consultatieService.findByPacientId(pacientId);

        model.addAttribute("pacient", pacient);
        model.addAttribute("consultatii", consultatii);
        
        return "consultatii/lista"; 
    }
    
   

    @GetMapping("/form")
    public String showConsultatieForm(@RequestParam Long pacientId, Model model) {
        Pacient pacient = pacientService.getPacient(pacientId);
        
        // CREĂM DTO-ul ȘI SETĂM ID-ul AICI
        CreateConsultatieRequest request = new CreateConsultatieRequest();
        request.setPacientId(pacientId);
        
        model.addAttribute("pacient", pacient);
        model.addAttribute("request", request);
        model.addAttribute("medicamente", medicamentService.getAll());
        
        return "consultatii/formular";
}

    // Procesează formularul
    @PostMapping("/save")
    public String saveConsultatie(@ModelAttribute CreateConsultatieRequest request, Model model) {
        
        try {
            
            consultatieService.createConsultatie(request); 
            return "redirect:/consultatii?pacientId=" + request.getPacientId();

        } catch (AccessDeniedException e) {
            // Dacă Medicul nu are Vizită înregistrată, Service-ul aruncă această excepție.
            model.addAttribute("eroareSecuritate", e.getMessage());
            // Reîncarcă lista de Consultații cu mesajul de eroare
            return listConsultatiiForPacient(request.getPacientId(), model);
            
        } catch (RuntimeException e) {
            // Alte erori (Pacient/Medicament negăsit)
            model.addAttribute("eroare", "Eroare la salvare: " + e.getMessage());
            return "consultatii/lista"; 
        }
    }
    
    
    @GetMapping("/delete/{id}")
    public String deleteConsultatie(@PathVariable Long id) {
        
        // --- SECURITATE ---
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        boolean isAdmin = auth.getAuthorities().stream()
                .anyMatch(r -> r.getAuthority().equals("ROLE_ADMIN"));

        if (!isAdmin) {
            throw new AccessDeniedException("Doar Administratorul poate șterge consultații!");
        }
        // ------------------

        consultatieService.deleteConsultatie(id);
        
        
        return "redirect:/pacienti"; 
    }
}