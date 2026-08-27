
package com.alexandra.clinica.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            // dezactivează CSRF (pentru API-uri)
            .csrf(csrf -> csrf.disable()) 

            //regulilor de autorizare
            .authorizeHttpRequests(authorize -> authorize
                // Permite accesul NEAUTENTIFICAT la pagina de login și resurse
                .requestMatchers("/login", "/css/**", "/js/**", "/images/**").permitAll()
                
                // Permite accesul tuturor utilizatorilor AUTENTIFICAȚI (inclusiv Admin/Medic)
                .requestMatchers("/pacienti/**", "/vizite/**", "/consultatii/**", "/dashboard").authenticated()
                
                // Nivelul de admin (API-uri sensibile)
                .requestMatchers("/api/medici/**", "/api/medicament/**", "/api/pacienti/**").hasRole("ADMIN") 
                
                // Nivelul de medic/admin (API-uri flux de lucru)
                .requestMatchers("/api/consultatii/**", "/api/vizite/**").hasAnyRole("ADMIN", "MEDIC") 
                
                .anyRequest().authenticated()
            ) 
            
            
            .formLogin(form -> form
                .loginPage("/login")          // formular GET
                .loginProcessingUrl("/login") // trimite POST-ul
                .defaultSuccessUrl("/dashboard", true) // Redirecționarea după succes
                .failureUrl("/login?error")   // Redirecționarea după eșec
                .permitAll()
            )
            
            .logout(logout -> logout
                .logoutUrl("/logout")
                .logoutSuccessUrl("/login?logout")
                .permitAll()
            )
            
            .httpBasic(withDefaults());

        return http.build();
    }
    
    // Bean necesar pentru criptarea parolelor
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}