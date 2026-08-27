package com.alexandra.clinica.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import com.alexandra.clinica.entity.Consultatie;




public interface ConsultatieRepository extends JpaRepository<Consultatie, Long> {
    List<Consultatie> findByPacient_PacientId(Long pacientId);
}
