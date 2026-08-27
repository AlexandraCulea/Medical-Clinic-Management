

package com.alexandra.clinica.controller;

import com.alexandra.clinica.controller.dto.CreateVizitaRequest;
import com.alexandra.clinica.entity.Vizita;
import com.alexandra.clinica.service.VizitaService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/vizite")
@CrossOrigin
public class VizitaController {
   
    @Autowired
    private VizitaService vizitaService;

    
    @PostMapping
    public ResponseEntity<Vizita> createVizita(@RequestBody CreateVizitaRequest createRequest) {
        Vizita vizitaNoua = vizitaService.createVizita(createRequest);
        return new ResponseEntity<>(vizitaNoua, HttpStatus.CREATED);
    }

    
    @PutMapping("/{id}")
    public ResponseEntity<Vizita> updateVizita(
        @PathVariable Long id,
        @RequestBody CreateVizitaRequest updateRequest // Primește corpul JSON
    ) {
        
        Vizita vizitaActualizata = vizitaService.updateVizita(id, updateRequest);
        return ResponseEntity.ok(vizitaActualizata);
    }

    @GetMapping
    public ResponseEntity<List<Vizita>> getAllVizite() {
    
    List<Vizita> vizite = vizitaService.findAll(); 
    return ResponseEntity.ok(vizite);
    }

    
    @GetMapping("/{id}")
    public ResponseEntity<Vizita> getVizitaById(@PathVariable Long id) {
        Vizita vizita = vizitaService.getVizitaById(id); 
        return ResponseEntity.ok(vizita);
    }
}
