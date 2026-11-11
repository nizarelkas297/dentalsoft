package ma.dentalsoft.domain;

import java.math.BigDecimal;

public class Acte {

    private Long id;
    private Long consultationId;
    private String libelle;
    private BigDecimal prix;
    private Integer quantite;

    public Acte() {
        this.quantite = 1;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Long getConsultationId() { return consultationId; }
    public void setConsultationId(Long consultationId) { this.consultationId = consultationId; }

    public String getLibelle() { return libelle; }
    public void setLibelle(String libelle) { this.libelle = libelle; }

    public BigDecimal getPrix() { return prix; }
    public void setPrix(BigDecimal prix) { this.prix = prix; }

    public Integer getQuantite() { return quantite; }
    public void setQuantite(Integer quantite) { this.quantite = quantite; }
}
