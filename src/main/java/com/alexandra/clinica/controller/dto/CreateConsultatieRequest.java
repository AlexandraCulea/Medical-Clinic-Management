
package com.alexandra.clinica.controller.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDate;

@Data
public class CreateConsultatieRequest {
    private Long pacientId;
    private Long medicamentId;

    @JsonFormat(pattern = "yyyy-MM-dd") // se potrivește cu "2025-07-10"
    private LocalDate data;

    private String diagnostic;
    private String dozaMedicament;
}
