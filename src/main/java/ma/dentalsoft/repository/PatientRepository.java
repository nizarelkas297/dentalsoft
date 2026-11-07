
package ma.dentalsoft.repository;

import ma.dentalsoft.domain.Patient;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public interface PatientRepository {

    List<Patient> findAll() throws SQLException;

    Optional<Patient> findById(Long id)
            throws SQLException;

    List<Patient> findByNom(String nom)
            throws SQLException;

    Patient save(Patient patient, Long medecinId)
            throws SQLException;

    boolean update(Patient patient)
            throws SQLException;

    boolean archive(Long id)
            throws SQLException;
}
