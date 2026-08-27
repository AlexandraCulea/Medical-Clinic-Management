package com.alexandra.clinica.service;

import java.time.LocalDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alexandra.clinica.controller.dto.CreateConsultatieRequest;
import com.alexandra.clinica.entity.Consultatie;
import com.alexandra.clinica.repository.ConsultatieRepository;
import com.alexandra.clinica.entity.Consultatie;
import com.alexandra.clinica.entity.Medicament;
import com.alexandra.clinica.entity.Pacient;
import com.alexandra.clinica.repository.ConsultatieRepository;
import com.alexandra.clinica.repository.MedicamentRepository;
import com.alexandra.clinica.repository.PacientRepository;
import com.alexandra.clinica.repository.VizitaRepository;
import com.alexandra.clinica.entity.Medic;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.access.AccessDeniedException;
import java.time.LocalDate;
import java.util.List;


@Service
public class ConsultatieService {

    private final ConsultatieRepository consultatii;
    private final PacientRepository pacienti;
    private final MedicamentRepository medicamente;
    private final VizitaRepository vizitaRepository;


    public ConsultatieService(ConsultatieRepository consultatii,
                              PacientRepository pacienti,
                              MedicamentRepository medicamente,
                              VizitaRepository vizitaRepository) {
        this.consultatii = consultatii;
        this.pacienti = pacienti;
        this.medicamente = medicamente;
        this.vizitaRepository = vizitaRepository;
    }

   

    @Transactional
    public Consultatie createConsultatie(CreateConsultatieRequest request) { 
    
       
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        
        
        //cast pt a ne asigura ca e vb de medic
        Medic medicAutentificat = (Medic) authentication.getPrincipal(); 
        Long medicId = medicAutentificat.getMedicId(); 

        
        boolean vizitaExista = vizitaRepository
            .findByMedicMedicIdAndPacientPacientId(medicId, request.getPacientId())
            .isPresent();

        if (!vizitaExista) {
            
            throw new AccessDeniedException("Nu se poate crea o consultație. Nu există nicio vizită (interacțiune) activă a Medicului curent pentru acest Pacient.");
        }
        
        
        Pacient p = pacienti.findById(request.getPacientId())
                .orElseThrow(() -> new RuntimeException("Pacientul cu ID " + request.getPacientId() + " nu a fost găsit."));
        Medicament m = medicamente.findById(request.getMedicamentId())
                .orElseThrow(() -> new RuntimeException("Medicamentul cu ID " + request.getMedicamentId() + " nu a fost găsit."));

       
        Consultatie c = new Consultatie();
        c.setPacient(p);
        c.setMedicament(m);
        c.setData(request.getData());
        c.setDiagnostic(request.getDiagnostic());
        c.setDozaMedicament(request.getDozaMedicament()); 

        return consultatii.save(c);
    }

    public List<Consultatie> findByPacientId(Long pacientId) {
        return consultatii.findByPacient_PacientId(pacientId);
    }
    

    @Transactional
    public Consultatie updateConsultatie(Long id, CreateConsultatieRequest request) {
        
        Consultatie consultatieExistenta = consultatii.findById(id)
            .orElseThrow(() -> new RuntimeException("Consultatia cu ID " + id + " nu a fost găsită!")); 
            
        
        if (request.getData() != null) {
            consultatieExistenta.setData(request.getData());
        }
        if (request.getDiagnostic() != null) {
            consultatieExistenta.setDiagnostic(request.getDiagnostic());
        }
        if (request.getDozaMedicament() != null) { 
            consultatieExistenta.setDozaMedicament(request.getDozaMedicament());
        }
        
        
        
        
        Long requestPacientId = request.getPacientId();
        if (requestPacientId != null && !requestPacientId.equals(consultatieExistenta.getPacient().getPacientId())) {
            Pacient pacientNou = pacienti.findById(requestPacientId)
                .orElseThrow(() -> new RuntimeException("Pacientul cu ID " + requestPacientId + " negăsit!"));
            consultatieExistenta.setPacient(pacientNou);
        }
        
        
        Long requestMedicamentId = request.getMedicamentId();
        if (requestMedicamentId != null && !requestMedicamentId.equals(consultatieExistenta.getMedicament().getMedicamentId())) {
            Medicament medicamentNou = medicamente.findById(requestMedicamentId)
                .orElseThrow(() -> new RuntimeException("Medicamentul cu ID " + requestMedicamentId + " negăsit!"));
            consultatieExistenta.setMedicament(medicamentNou);
        }
        
        return consultatii.save(consultatieExistenta);
    }
    
 
    public Consultatie getConsultatie(Long id) {
        return consultatii.findById(id).orElseThrow();
    }

    public List<Consultatie> getAll() {
        return consultatii.findAll();
    }

    @Transactional
    public void deleteConsultatie(Long id) {
        consultatii.deleteById(id);
    }
}
