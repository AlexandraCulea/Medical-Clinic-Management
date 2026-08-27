package com.alexandra.clinica.controller.web;

import com.alexandra.clinica.entity.Medicament;
import com.alexandra.clinica.service.MedicamentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;

@Controller
@RequestMapping("/medicamente")
public class MedicamentWebController {

    @Autowired
    private MedicamentService medicamentService;

    
    @GetMapping
    public String listMedicamente(Model model) {
        model.addAttribute("medicamente", medicamentService.getAll());
        return "medicamente/lista";
    }

    
    @GetMapping("/form")
    public String showForm(@RequestParam(required = false) Long id, Model model) {
        Medicament medicament = (id == null) ? new Medicament() : medicamentService.getMedicament(id);
        model.addAttribute("medicament", medicament);
        return "medicamente/formular";
    }

    
    @PostMapping("/save")
    public String saveMedicament(@ModelAttribute Medicament medicament) {
        medicamentService.saveMedicament(medicament);
        return "redirect:/medicamente";
    }

    
    @GetMapping("/delete/{id}")
    public String deleteMedicament(@PathVariable Long id) {
        
        //Doar Adminul șterge 
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        boolean isAdmin = auth.getAuthorities().stream()
                .anyMatch(r -> r.getAuthority().equals("ROLE_ADMIN"));

        if (!isAdmin) {
            throw new AccessDeniedException("Doar Administratorul poate șterge medicamente din farmacie!");
        }
        //
        medicamentService.deleteMedicament(id);
        return "redirect:/medicamente";
    }
}