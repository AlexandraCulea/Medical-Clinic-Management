
package com.alexandra.clinica.entity;

import jakarta.persistence.*;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;



@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "consultatii")
public class Consultatie {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long consultatieId;

    @ManyToOne
    @JoinColumn(name = "pacient_id", nullable = false)
    private Pacient pacient;

    @ManyToOne
    @JoinColumn(name = "medicament_id", nullable = false)
    @JsonIgnoreProperties({"vizite", "consultatii"})
    private Medicament medicament;

    private LocalDate data;
    private String diagnostic;
    private String dozaMedicament;

}