package com.alexandra.clinica;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Hello {

    @GetMapping("/api/hello")
    public String hello() {
        return "✅ Merge Spring Boot + MySQL!";
    }
}