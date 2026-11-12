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
public class Prescription {
    private Long id;
    private Long ordonnanceId;
    private Long medicamentId;
    private String nomMedicamentSnapshot;
    private int quantite;
    private String frequence;
    private int dureeJours;
}