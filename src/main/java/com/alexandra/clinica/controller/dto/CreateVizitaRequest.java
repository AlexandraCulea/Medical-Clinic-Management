package com.alexandra.clinica.controller.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

import java.time.LocalDate;

@Data
public class CreateVizitaRequest {
    private Long medicId;
    private Long pacientId;

    @JsonFormat(pattern = "yyyy-MM-dd")  // așteaptă 2025-10-22

    @JsonProperty("data")
    private LocalDate data;

    private String urgenta;
}
