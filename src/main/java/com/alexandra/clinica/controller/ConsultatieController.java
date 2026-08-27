
package com.alexandra.clinica.controller;

import com.alexandra.clinica.controller.dto.CreateConsultatieRequest;
import com.alexandra.clinica.entity.Consultatie;
import com.alexandra.clinica.service.ConsultatieService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/consultatii")
@CrossOrigin
public class ConsultatieController {

    private final ConsultatieService consultatieService;

    
    public ConsultatieController(ConsultatieService consultatieService) {
        this.consultatieService = consultatieService;
    }

    //Folosește DTO
    @PostMapping
    public ResponseEntity<Consultatie> createConsultatie(@RequestBody CreateConsultatieRequest request) {
        
        Consultatie nouaConsultatie = consultatieService.createConsultatie(request);
        return new ResponseEntity<>(nouaConsultatie, HttpStatus.CREATED); // Returnează 201 CREATED
    }

    @PutMapping("/{id}")
    public ResponseEntity<Consultatie> updateConsultatie(
        @PathVariable Long id,
        @RequestBody CreateConsultatieRequest request // Primește DTO-ul complet
    ) {
        Consultatie consultatieActualizata = consultatieService.updateConsultatie(id, request);
        return ResponseEntity.ok(consultatieActualizata); // Returnează 200 OK
    }

    
    @GetMapping
    public ResponseEntity<List<Consultatie>> getAllConsultatii() {
        List<Consultatie> consultatii = consultatieService.getAll();
        return ResponseEntity.ok(consultatii);
    }

   
    @GetMapping("/{id}")
    public ResponseEntity<Consultatie> getConsultatieById(@PathVariable Long id) {
        Consultatie consultatie = consultatieService.getConsultatie(id);
        return ResponseEntity.ok(consultatie);
    }
    
   
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT) // Returnează 204 NO CONTENT
    public void deleteConsultatie(@PathVariable Long id) {
        consultatieService.deleteConsultatie(id);
    }
}



