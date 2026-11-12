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
public class Consultation {
    private Long id;
    private Long dossierId;
    private Long medecinId;
    private Long rdvId;
    private LocalDateTime dateConsultation;
    private String observation;
    private String statut = "BROUILLON";
}