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
public class Intervention {
    private Long id;
    private Long consultationId;
    private Long acteId;
    private String libelleActeSnapshot;
    private String dent;
    private int quantite;
    private BigDecimal prixUnitaire;
}