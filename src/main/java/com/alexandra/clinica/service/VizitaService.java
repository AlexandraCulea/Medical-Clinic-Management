package com.alexandra.clinica.service;

import java.time.LocalDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alexandra.clinica.entity.Vizita;
import com.alexandra.clinica.repository.VizitaRepository;

import com.alexandra.clinica.entity.Vizita;
import com.alexandra.clinica.controller.dto.CreateVizitaRequest;
import com.alexandra.clinica.entity.Medic;
import com.alexandra.clinica.entity.Pacient;
import com.alexandra.clinica.repository.VizitaRepository;
import com.alexandra.clinica.repository.MedicRepository;
import com.alexandra.clinica.repository.PacientRepository;
import java.time.LocalDate;
import java.util.List;
import org.springframework.security.core.Authentication;
import org.springframework.security.access.AccessDeniedException;



@Service
public class VizitaService {


    @Autowired
    private VizitaRepository vizitaRepository;
    @Autowired
    private MedicRepository medicRepository;
    @Autowired
    private PacientRepository pacientRepository;

    @Transactional
     
    public Vizita updateVizita(Long id, CreateVizitaRequest request) {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    Medic medicAutentificat = (Medic) authentication.getPrincipal(); 
    
    Vizita vizitaExistenta = vizitaRepository.findById(id).orElseThrow(); 


    
    if (!vizitaExistenta.getMedic().getMedicId().equals(medicAutentificat.getMedicId())) {
         
         throw new AccessDeniedException("Nu ai permisiunea de a modifica această vizită.");
    }
    
    
    return vizitaRepository.save(vizitaExistenta);
    } 

    public List<Vizita> findByPacientId(Long pacientId) {
        
        return vizitaRepository.findByPacient_PacientId(pacientId);
    }
    public Vizita createVizita(CreateVizitaRequest createRequest) {
        
        Medic medic = medicRepository.findById(createRequest.getMedicId())
                .orElseThrow(() -> new RuntimeException("Medic negăsit!"));
        Pacient pacient = pacientRepository.findById(createRequest.getPacientId())
                .orElseThrow(() -> new RuntimeException("Pacient negăsit!"));
        
        Vizita nouaVizita = new Vizita(); 
        nouaVizita.setMedic(medic);
        nouaVizita.setPacient(pacient);
        nouaVizita.setData(createRequest.getData());
        nouaVizita.setUrgenta(createRequest.getUrgenta());

        return vizitaRepository.save(nouaVizita);
    }
    public List<Vizita> findAll() {
        return vizitaRepository.findAll();
    }
    
    
    public Vizita getVizitaById(Long id) {
        return vizitaRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Vizita nu a fost găsită!"));
    }

    public void deleteVizita(Long id) {
    vizitaRepository.deleteById(id);
}
}
