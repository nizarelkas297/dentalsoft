package ma.dentalsoft.domain;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/** Table SQL : voir schema.sql. */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Facture {
    private Long id;
    private Long consultationId;
    private String numero;
    private LocalDateTime dateFacture;
    private BigDecimal total;
    private String statut = "BROUILLON";
    private String motifAnnulation;
}