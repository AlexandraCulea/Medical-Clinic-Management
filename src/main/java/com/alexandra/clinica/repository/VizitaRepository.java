package com.alexandra.clinica.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.alexandra.clinica.entity.Vizita;
import java.util.List;

public interface VizitaRepository extends JpaRepository<Vizita, Long> {
    java.util.Optional<Vizita> findByMedicMedicIdAndPacientPacientId(Long medicId, Long pacientId);
    List<Vizita> findByPacient_PacientId(Long pacientId);
}
