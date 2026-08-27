// package com.alexandra.clinica.entity;

// import jakarta.persistence.*;
// import java.time.LocalDate;
// import lombok.AllArgsConstructor;
// import lombok.Builder;
// import lombok.Data;
// import lombok.NoArgsConstructor;


// @Entity
// @Data
// @NoArgsConstructor
// @AllArgsConstructor
// @Builder
// @Table(name = "vizite")
// public class Vizita {
//     @Id
//     @GeneratedValue(strategy = GenerationType.IDENTITY)
//     private Long vizitaId;

//     @ManyToOne(fetch = FetchType.LAZY)
//     @JoinColumn(name = "medic_id")
//     private Medic medic;

//     @ManyToOne(fetch = FetchType.LAZY)
//     @JoinColumn(name = "pacient_id")
//     private Pacient pacient;

//     private LocalDate data_vizita;
//     private boolean urgenta;

//     // getters/setters
// }

package com.alexandra.clinica.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name = "vizite")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Vizita {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long vizitaId;

    @ManyToOne
    @JoinColumn(name = "medic_id", nullable = false)
    @JsonIgnoreProperties({"vizite"})
    private Medic medic;

    @ManyToOne
    @JoinColumn(name = "pacient_id", nullable = false)
    @JsonIgnoreProperties({"vizite", "consultatii"})
    private Pacient pacient;

    private LocalDate data;
    private String urgenta;
}
