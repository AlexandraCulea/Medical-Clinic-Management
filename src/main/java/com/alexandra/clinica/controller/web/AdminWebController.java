package com.alexandra.clinica.controller.web;

import com.alexandra.clinica.entity.Medic;
import com.alexandra.clinica.service.MedicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/admin")
public class AdminWebController {

    @Autowired
    private MedicService medicService;

    //Lista de useri (medici)
    @GetMapping("/utilizatori")
    public String listUsers(Model model) {
        model.addAttribute("medici", medicService.getAll());
        return "admin/utilizatori"; // Caută în templates/admin/utilizatori.html
    }

    //Formular de adaugare/editare
    @GetMapping("/utilizatori/form")
    public String showUserForm(@RequestParam(required = false) Long id, Model model) {
        Medic medic;
        if (id != null) {
            medic = medicService.getMedic(id);
        } else {
            medic = new Medic();
        }
        model.addAttribute("medic", medic);
        return "admin/formular"; // Caută în templates/admin/formular.html
    }

    
    @PostMapping("/utilizatori/save")
    public String saveUser(@ModelAttribute Medic medic) {
        // Service: criptare parole si roluri
        medicService.createMedic(medic); 
        return "redirect:/admin/utilizatori";
    }

    
    @GetMapping("/utilizatori/delete/{id}")
    public String deleteUser(@PathVariable Long id) {
        medicService.deleteMedic(id);
        return "redirect:/admin/utilizatori";
    }
}