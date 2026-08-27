package com.alexandra.clinica.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.alexandra.clinica.entity.Medicament;

public interface MedicamentRepository extends JpaRepository<Medicament, Long> {
}
