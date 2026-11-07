
package ma.dentalsoft.service;

import ma.dentalsoft.domain.Patient;
import ma.dentalsoft.repository.PatientRepository;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public class PatientService {

    private final PatientRepository repository;

    public PatientService(PatientRepository repository) {
        this.repository = repository;
    }

    public List<Patient> getAllPatients()
            throws SQLException {

        return repository.findAll();
    }

    public Optional<Patient> getPatientById(Long id)
            throws SQLException {

        return repository.findById(id);
    }

    public List<Patient> searchPatients(String nom)
            throws SQLException {

        if (nom == null) {
            throw new IllegalArgumentException(
                    "Nom de recherche obligatoire"
            );
        }

        return repository.findByNom(nom.trim());
    }

    public Patient addPatient(Patient patient,
                              Long medecinId)
            throws SQLException {

        validatePatient(patient);

        if (medecinId == null || medecinId <= 0) {
            throw new IllegalArgumentException(
                    "Identifiant du medecin invalide"
            );
        }

        return repository.save(patient, medecinId);
    }

    public boolean updatePatient(Patient patient)
            throws SQLException {

        validatePatient(patient);

        if (patient.getId() == null ||
                patient.getId() <= 0) {
            throw new IllegalArgumentException(
                    "Identifiant du patient invalide"
            );
        }

        return repository.update(patient);
    }

    public boolean archivePatient(Long id)
            throws SQLException {

        if (id == null || id <= 0) {
            throw new IllegalArgumentException(
                    "Identifiant invalide"
            );
        }

        return repository.archive(id);
    }

    private void validatePatient(Patient patient) {

        if (patient == null ||
                patient.getNom() == null ||
                patient.getNom().isBlank() ||
                patient.getPrenom() == null ||
                patient.getPrenom().isBlank() ||
                patient.getDateNaissance() == null) {

            throw new IllegalArgumentException(
                    "Nom, prenom et date de naissance obligatoires"
            );
        }

        if (patient.getDateNaissance().isAfter(
                LocalDate.now())) {

            throw new IllegalArgumentException(
                    "La date de naissance est invalide"
            );
        }
    }
}
