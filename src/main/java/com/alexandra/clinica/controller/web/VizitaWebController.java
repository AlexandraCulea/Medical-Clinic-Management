package com.alexandra.clinica.controller.web;

import com.alexandra.clinica.entity.Vizita;
import com.alexandra.clinica.entity.Pacient;
import com.alexandra.clinica.controller.dto.CreateVizitaRequest;
import com.alexandra.clinica.entity.Medic;
import com.alexandra.clinica.service.VizitaService;
import com.alexandra.clinica.service.PacientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;

@Controller
@RequestMapping("/vizite")
public class VizitaWebController {

    @Autowired
    private VizitaService vizitaService;
    
    @Autowired
    private PacientService pacientService;

    
    @GetMapping
    public String listViziteForPacient(@RequestParam Long pacientId, Model model) {
        
       
        Pacient pacient = pacientService.getPacient(pacientId); 
        
        
        List<Vizita> vizite = vizitaService.findByPacientId(pacientId); 

        model.addAttribute("pacient", pacient);
        model.addAttribute("vizite", vizite);
        
        return "vizite/lista"; 
    }
    
    
    @GetMapping("/form")
    public String showVizitaForm(@RequestParam Long pacientId, Model model) {
        Pacient pacient = pacientService.getPacient(pacientId);
        
        
        CreateVizitaRequest request = new CreateVizitaRequest();
        request.setPacientId(pacientId);

        model.addAttribute("pacient", pacient);
        model.addAttribute("request", request);
        
        return "vizite/formular";
    }

    
    @PostMapping("/save")
    public String saveVizita(@ModelAttribute CreateVizitaRequest request) {
        
        
        // Nu lăsăm utilizatorul să aleagă medicul din formular.
        // Luăm automat Medicul care este LOGAT în sistem.
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        
        if (authentication.getPrincipal() instanceof Medic) {
             Medic medicAutentificat = (Medic) authentication.getPrincipal();
             request.setMedicId(medicAutentificat.getMedicId());
        } else {

             throw new AccessDeniedException("Trebuie să fii logat ca Medic pentru a înregistra o vizită.");
        }

        
        vizitaService.createVizita(request);

        
        return "redirect:/vizite?pacientId=" + request.getPacientId();
    }

    @GetMapping("/delete/{id}")
public String deleteVizita(@PathVariable Long id) {
        
       
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        boolean isAdmin = auth.getAuthorities().stream()
                .anyMatch(r -> r.getAuthority().equals("ROLE_ADMIN"));

        if (!isAdmin) {
            throw new AccessDeniedException("Doar Administratorul poate șterge vizite!");
        }
        

        
        vizitaService.deleteVizita(id);
        return "redirect:/pacienti"; 
    }
}