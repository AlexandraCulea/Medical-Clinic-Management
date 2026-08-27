package com.alexandra.clinica.service;

import com.alexandra.clinica.entity.Medicament;
import com.alexandra.clinica.repository.MedicamentRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class MedicamentService {
    private final MedicamentRepository medicamentRepository;

    public MedicamentService(MedicamentRepository repo) { this.medicamentRepository = repo; }

    @Transactional
    public Medicament createMedicament(Medicament m) { return medicamentRepository.save(m); }

    public Medicament getMedicament(Long id) { return medicamentRepository.findById(id).orElseThrow(); }

    public void saveMedicament(Medicament medicament) {
        medicamentRepository.save(medicament);
    }

    public List<Medicament> getAll() { return medicamentRepository.findAll(); }

    @Transactional
    public Medicament updateMedicament(Long id, Medicament src) {
        Medicament m = medicamentRepository.findById(id).orElseThrow();
        m.setDenumire(src.getDenumire());
        return medicamentRepository.save(m);
    }

    @Transactional
    public void deleteMedicament(Long id) { medicamentRepository.deleteById(id); }
}
