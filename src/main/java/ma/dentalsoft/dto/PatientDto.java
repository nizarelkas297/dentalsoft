
package ma.dentalsoft.dto;

import java.time.LocalDate;

public class PatientDto {

    private Long id;
    private String nom;
    private String prenom;
    private LocalDate dateNaissance;
    private String sexe;
    private String cin;
    private String passeport;
    private String email;
    private String telephone;
    private String adresse;
    private String assurance;
    private boolean archive;

    public PatientDto() {
    }

    public PatientDto(Long id, String nom,
                      String prenom,
                      LocalDate dateNaissance,
                      String telephone) {
        this.id = id;
        this.nom = nom;
        this.prenom = prenom;
        this.dateNaissance = dateNaissance;
        this.telephone = telephone;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getNom() { return nom; }
    public void setNom(String nom) { this.nom = nom; }

    public String getPrenom() { return prenom; }
    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public LocalDate getDateNaissance() {
        return dateNaissance;
    }
    public void setDateNaissance(LocalDate dateNaissance) {
        this.dateNaissance = dateNaissance;
    }

    public String getSexe() { return sexe; }
    public void setSexe(String sexe) { this.sexe = sexe; }

    public String getCin() { return cin; }
    public void setCin(String cin) { this.cin = cin; }

    public String getPasseport() { return passeport; }
    public void setPasseport(String passeport) {
        this.passeport = passeport;
    }

    public String getEmail() { return email; }
    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelephone() { return telephone; }
    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getAdresse() { return adresse; }
    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public String getAssurance() { return assurance; }
    public void setAssurance(String assurance) {
        this.assurance = assurance;
    }

    public boolean isArchive() { return archive; }
    public void setArchive(boolean archive) {
        this.archive = archive;
    }
}
