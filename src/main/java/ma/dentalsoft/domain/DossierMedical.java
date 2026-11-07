
package ma.dentalsoft.domain;

import java.time.LocalDateTime;

public class DossierMedical {

    private Long id;
    private Long patientId;
    private Long medecinId;
    private LocalDateTime dateCreation;

    public DossierMedical() {
    }

    public DossierMedical(Long patientId, Long medecinId) {
        this.patientId = patientId;
        this.medecinId = medecinId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getPatientId() {
        return patientId;
    }

    public void setPatientId(Long patientId) {
        this.patientId = patientId;
    }

    public Long getMedecinId() {
        return medecinId;
    }

    public void setMedecinId(Long medecinId) {
        this.medecinId = medecinId;
    }

    public LocalDateTime getDateCreation() {
        return dateCreation;
    }

    public void setDateCreation(LocalDateTime dateCreation) {
        this.dateCreation = dateCreation;
    }
}
