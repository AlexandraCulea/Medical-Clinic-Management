package com.alexandra.clinica.service;

import com.alexandra.clinica.entity.Pacient;
import com.alexandra.clinica.repository.PacientRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class PacientService {
    private final PacientRepository pacientRepository;

    public PacientService(PacientRepository repo) { this.pacientRepository = repo; }

    @Transactional
    public Pacient createPacient(Pacient p) { return pacientRepository.save(p); }

    public Pacient getPacient(Long id) { return pacientRepository.findById(id).orElseThrow(); }

    public List<Pacient> getAll() { return pacientRepository.findAll(); }

    @Transactional
    public Pacient updatePacient(Long id, Pacient src) {
        Pacient p = pacientRepository.findById(id).orElseThrow();
        p.setCnp(src.getCnp());
        p.setNumePacient(src.getNumePacient());
        p.setPrenumePacient(src.getPrenumePacient());
        p.setAdresa(src.getAdresa());
        p.setAsigurare(src.getAsigurare());
        return pacientRepository.save(p);
    }

    public Pacient save(Pacient pacient) {
        return pacientRepository.save(pacient);
    }

    @Transactional
    public void deletePacient(Long id) { pacientRepository.deleteById(id); }
}
