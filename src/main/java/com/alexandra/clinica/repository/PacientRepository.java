package com.alexandra.clinica.repository;
import org.springframework.data.jpa.repository.JpaRepository;

import com.alexandra.clinica.entity.Pacient;

public interface PacientRepository extends JpaRepository<Pacient, Long> {
   
}