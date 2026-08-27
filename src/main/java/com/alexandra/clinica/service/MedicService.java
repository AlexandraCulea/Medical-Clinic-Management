
package com.alexandra.clinica.service;

import com.alexandra.clinica.entity.Medic;
import com.alexandra.clinica.repository.MedicRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.security.crypto.password.PasswordEncoder; 

import java.util.List;

@Service
public class MedicService {
    
    private final MedicRepository medic;
    private final PasswordEncoder passwordEncoder;

    
    public MedicService(MedicRepository medic, PasswordEncoder passwordEncoder) { 
        this.medic = medic;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    public Medic createMedic(Medic m) { 
        //Criptarea parolei
        String rawPassword = m.getPassword();
        if (rawPassword != null && !rawPassword.isEmpty()) {
            m.setPassword(passwordEncoder.encode(rawPassword));
        } else {
            
            throw new IllegalArgumentException("Parola nu poate fi goală pentru un medic nou.");
        }
        
        
        if (m.getRole() == null || m.getRole().isEmpty()) {
            m.setRole("MEDIC"); 
        }

        return medic.save(m); 
    }

    public Medic getMedic(Long id) { return medic.findById(id).orElseThrow(); }

    public List<Medic> getAll() { return medic.findAll(); }

    @Transactional
    public void initializeMissingSecurityFields() {
        List<Medic> medici = medic.findAll();
        
        // Setăm un username, rol și parolă de bază pentru medicii fără date de securitate
        medici.stream()
            .filter(m -> m.getUsername() == null || m.getRole() == null)
            .forEach(m -> {
                
                String baseUsername = (m.getPrenumeMedic() != null ? m.getPrenumeMedic().toLowerCase().substring(0, 3) : "doc")
                                    + (m.getNumeMedic() != null ? m.getNumeMedic().toLowerCase().substring(0, 3) : "tor");
                
                
                m.setUsername(baseUsername);
                m.setRole("MEDIC"); // Rol implicit la medic
                
                
                String initialPassword = "changeme123";
                m.setPassword(passwordEncoder.encode(initialPassword));
                
                
            });

        medic.saveAll(medici); 
        System.out.println("--- " + (medici.size() - medic.findAll().stream().filter(m -> m.getUsername() != null).count()) + " Medici inițializați cu rol de MEDIC. ---");
}

    @Transactional
    public Medic updateMedic(Long id, Medic src) {
        Medic m = medic.findById(id).orElseThrow();
        
        
        m.setNumeMedic(src.getNumeMedic());
        m.setPrenumeMedic(src.getPrenumeMedic());
        m.setSpecializare(src.getSpecializare());
        
        
        String newRawPassword = src.getPassword();
        if (newRawPassword != null && !newRawPassword.isEmpty()) {
            m.setPassword(passwordEncoder.encode(newRawPassword));
        }
        
       
        if (src.getRole() != null) {
            m.setRole(src.getRole());
        }

        return medic.save(m);
    }

    @Transactional
    public void deleteMedic(Long id) { medic.deleteById(id); }
}