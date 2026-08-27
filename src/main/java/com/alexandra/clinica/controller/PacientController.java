package com.alexandra.clinica.controller;

import com.alexandra.clinica.entity.Pacient;
import com.alexandra.clinica.service.PacientService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/pacienti")
@CrossOrigin
public class PacientController {
    private final PacientService pacientService;

    public PacientController(PacientService service) { this.pacientService = service; }

    @PostMapping
    public Pacient createPacient(@RequestBody Pacient body) { return pacientService.createPacient(body); }

    @GetMapping("/{id}")
    public Pacient getPacient(@PathVariable Long id) { return pacientService.getPacient(id); }

    @GetMapping
    public List<Pacient> getAll() { return pacientService.getAll(); }

    @PutMapping("/{id}")
    public Pacient updatePacient(@PathVariable Long id, @RequestBody Pacient body) {
        return pacientService.updatePacient(id, body);
    }

    @DeleteMapping("/{id}")
    public void deletePacient(@PathVariable Long id) { pacientService.deletePacient(id); }
}
