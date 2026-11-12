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
public class Paiement {
    private Long id;
    private Long factureId;
    private Long auteurId;
    private String reference;
    private LocalDateTime datePaiement;
    private BigDecimal montant;
    private String mode;
}