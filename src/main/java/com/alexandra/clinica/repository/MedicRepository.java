package com.alexandra.clinica.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.alexandra.clinica.entity.Medic;

public interface MedicRepository extends JpaRepository<Medic, Long> {
    java.util.Optional<Medic> findByUsername(String username);
}