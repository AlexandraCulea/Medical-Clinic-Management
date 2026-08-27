
package com.alexandra.clinica.controller.web;

import com.alexandra.clinica.entity.Pacient;
import com.alexandra.clinica.service.PacientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;


@Controller
@RequestMapping("/pacienti")
public class PacientWebController {

    @Autowired
    private PacientService pacientService; 


    @GetMapping
    public String listPacienti(Model model) {
        model.addAttribute("pacienti", pacientService.getAll());
        return "pacienti/lista"; 
    }

    
    @GetMapping("/form")
    public String showPacientForm(@RequestParam(required = false) Long id, Model model) {
        Pacient pacient = (id == null) ? new Pacient() : pacientService.getPacient(id);
        model.addAttribute("pacient", pacient);
        return "pacienti/formular";
    }

   
    @PostMapping("/save")
    public String savePacient(@ModelAttribute Pacient pacient) {
        pacientService.save(pacient); 
        return "redirect:/pacienti";
    }

    
    @GetMapping("/delete/{id}")
    public String deletePacient(@PathVariable Long id) {
        
        
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        
        // Verific dacă userul are rolul de admin
        boolean isAdmin = auth.getAuthorities().stream()
                .anyMatch(r -> r.getAuthority().equals("ROLE_ADMIN"));

        if (!isAdmin) {
            // Dacă nu e admin, nu ștergem nimic
            throw new AccessDeniedException("Doar Administratorul poate șterge pacienți!");
        }

        pacientService.deletePacient(id);
        return "redirect:/pacienti";
    }
}