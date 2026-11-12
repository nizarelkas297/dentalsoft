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
public class RendezVous {
    private Long id;
    private Long dossierId;
    private Long medecinId;
    private LocalDateTime debut;
    private LocalDateTime fin;
    private String motif;
    private String statut = "PLANIFIE";
    private LocalDateTime arriveeAt;
}