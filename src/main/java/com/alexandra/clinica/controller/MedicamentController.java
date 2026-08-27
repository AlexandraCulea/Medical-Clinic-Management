package com.alexandra.clinica.controller;

import com.alexandra.clinica.entity.Medicament;
import com.alexandra.clinica.service.MedicamentService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/medicamente")
@CrossOrigin
public class MedicamentController {
    private final MedicamentService medicamentService;

    public MedicamentController(MedicamentService service) { this.medicamentService = service; }

    @PostMapping
    public Medicament createMedicament(@RequestBody Medicament body) { return medicamentService.createMedicament(body); }

    @GetMapping("/{id}")
    public Medicament getMedicament(@PathVariable Long id) { return medicamentService.getMedicament(id); }

    @GetMapping
    public List<Medicament> getAll() { return medicamentService.getAll(); }

    @PutMapping("/{id}")
    public Medicament updateMedicament(@PathVariable Long id, @RequestBody Medicament body) {
        return medicamentService.updateMedicament(id, body);
    }

    @DeleteMapping("/{id}")
    public void deleteMedicament(@PathVariable Long id) { medicamentService.deleteMedicament(id); }
}
