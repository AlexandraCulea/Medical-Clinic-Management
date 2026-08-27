# 🏥 Medical Clinic Management System

O aplicație web pentru gestionarea activității unei clinici medicale, dezvoltată cu **Java Spring Boot**, **MySQL** și **Thymeleaf**. Sistemul oferă autentificare securizată bazată pe roluri, gestiunea pacienților și consultațiilor, precum și un API REST integrat[cite: 1].

---

## 🚀 Funcționalități Cheie

- **Autentificare & Securitate:** Roluri distincte (`ADMIN` și `MEDIC`) cu parole criptate (BCrypt)[cite: 1].
- **Management Medical:** Înregistrare și actualizare pacienți, programare vizite și prescriere consultații/tratamente[cite: 1].
- **Catalog Medicamente:** Modul integrat pentru gestionarea stocului și tipurilor de medicamente[cite: 1].
- **Interfață Duală:**
  - Pagină web receptivă realizată cu **Thymeleaf** și **Bootstrap**[cite: 1].
  - **REST API** pentru integrări externe și testare rapidă[cite: 1].

---

## 🛠️ Tehnologii

- **Backend:** Java, Spring Boot (Spring MVC, Spring Data JPA, Spring Security)[cite: 1]
- **Bază de date:** MySQL (rulat via Docker)[cite: 1]
- **Frontend:** HTML5, Bootstrap, Thymeleaf[cite: 1]
- **Utilitare:** Maven, Postman[cite: 1]

---

## 📦 Pornire Rapidă

1. **Baza de date (Docker):**
   ```bash
   docker run --name mysql-clinica -e MYSQL_ROOT_PASSWORD=root -e MYSQL_DATABASE=clinica -p 3306:3306 -d mysql:8.0
