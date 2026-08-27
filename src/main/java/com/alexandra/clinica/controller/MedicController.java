package com.alexandra.clinica.controller;

import com.alexandra.clinica.entity.Medic;
import com.alexandra.clinica.service.MedicService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/medici")
@CrossOrigin
public class MedicController {
    private final MedicService medicService;

    public MedicController(MedicService service) { this.medicService = service; }

    @PostMapping
    public Medic createMedic(@RequestBody Medic body) { return medicService.createMedic(body); }

    @GetMapping("/{id}")
    public Medic getMedic(@PathVariable Long id) { return medicService.getMedic(id); }

    @GetMapping
    public List<Medic> getAll() { return medicService.getAll(); }

    @PutMapping("/{id}")
    public Medic updateMedic(@PathVariable Long id, @RequestBody Medic body) {
        return medicService.updateMedic(id, body);
    }

    @DeleteMapping("/{id}")
    public void deleteMedic(@PathVariable Long id) { medicService.deleteMedic(id); }
}
