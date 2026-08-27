
package com.alexandra.clinica.controller.web;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class SecurityController {

    
    @GetMapping("/login")
    public String login() {
        return "login"; 
    }

    
    @GetMapping("/dashboard")
    public String redirectToRoleSpecificDashboard() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        
        
        boolean isAdmin = auth.getAuthorities().stream()
            .anyMatch(r -> r.getAuthority().equals("ROLE_ADMIN")); 

        if (isAdmin) {
            
            return "redirect:/admin/utilizatori"; 
        } else {
            
            return "redirect:/pacienti"; 
        }
    }
}

