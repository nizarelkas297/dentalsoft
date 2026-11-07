
package ma.dentalsoft.repository;

import ma.dentalsoft.config.JdbcConnectionFactory;
import ma.dentalsoft.domain.Patient;
import ma.dentalsoft.shared.RowMappers;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class PatientRepoImpl implements PatientRepository {

    private final JdbcConnectionFactory factory;

    public PatientRepoImpl() {
        factory = JdbcConnectionFactory.getInstance();
    }

    @Override
    public List<Patient> findAll() throws SQLException {

        List<Patient> patients = new ArrayList<>();

        String sql = "SELECT * FROM patient ORDER BY id";

        try (Connection connection = factory.getConnection();
             PreparedStatement ps =
                     connection.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                patients.add(RowMappers.toPatient(rs));
            }
        }

        return patients;
    }

    @Override
    public Optional<Patient> findById(Long id)
            throws SQLException {

        String sql = "SELECT * FROM patient WHERE id = ?";

        try (Connection connection = factory.getConnection();
             PreparedStatement ps =
                     connection.prepareStatement(sql)) {

            ps.setLong(1, id);

            try (ResultSet rs = ps.executeQuery()) {

                if (rs.next()) {
                    return Optional.of(
                            RowMappers.toPatient(rs)
                    );
                }
            }
        }

        return Optional.empty();
    }

    @Override
    public List<Patient> findByNom(String nom)
            throws SQLException {

        List<Patient> patients = new ArrayList<>();

        String sql = """
            SELECT * FROM patient
            WHERE nom LIKE ?
            ORDER BY nom, prenom
            """;

        try (Connection connection = factory.getConnection();
             PreparedStatement ps =
                     connection.prepareStatement(sql)) {

            ps.setString(1, "%" + nom + "%");

            try (ResultSet rs = ps.executeQuery()) {

                while (rs.next()) {
                    patients.add(
                            RowMappers.toPatient(rs)
                    );
                }
            }
        }

        return patients;
    }

    @Override
    public Patient save(Patient patient, Long medecinId)
            throws SQLException {

        String checkMedecin = """
            SELECT COUNT(*)
            FROM utilisateur u
            JOIN utilisateur_role ur
              ON u.id = ur.utilisateur_id
            WHERE u.id = ?
              AND u.actif = TRUE
              AND ur.role_code = 'MEDECIN'
            """;

        String insertPatient = """
            INSERT INTO patient
            (nom, prenom, date_naissance, sexe,
             cin, passeport, email, telephone,
             adresse, assurance)
            VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)
            """;

        String insertDossier = """
            INSERT INTO dossier_medical
            (patient_id, medecin_id)
            VALUES (?, ?)
            """;

        try (Connection connection = factory.getConnection()) {

            connection.setAutoCommit(false);

            try {
                // Verifier que le medecin existe
                try (PreparedStatement ps =
                             connection.prepareStatement(
                                     checkMedecin
                             )) {

                    ps.setLong(1, medecinId);

                    try (ResultSet rs = ps.executeQuery()) {

                        rs.next();

                        if (rs.getInt(1) == 0) {
                            throw new SQLException(
                                    "Medecin introuvable ou inactif"
                            );
                        }
                    }
                }

                // Inserer le patient
                try (PreparedStatement ps =
                             connection.prepareStatement(
                                     insertPatient,
                                     Statement.RETURN_GENERATED_KEYS
                             )) {

                    ps.setString(1, patient.getNom());
                    ps.setString(2, patient.getPrenom());
                    ps.setDate(3, Date.valueOf(
                            patient.getDateNaissance()
                    ));
                    ps.setString(4, patient.getSexe());
                    ps.setString(5, patient.getCin());
                    ps.setString(6, patient.getPasseport());
                    ps.setString(7, patient.getEmail());
                    ps.setString(8, patient.getTelephone());
                    ps.setString(9, patient.getAdresse());
                    ps.setString(10, patient.getAssurance());

                    ps.executeUpdate();

                    try (ResultSet keys =
                                 ps.getGeneratedKeys()) {

                        if (keys.next()) {
                            patient.setId(keys.getLong(1));
                        } else {
                            throw new SQLException(
                                    "ID du patient non genere"
                            );
                        }
                    }
                }

                // Creer automatiquement le dossier medical
                try (PreparedStatement ps =
                             connection.prepareStatement(
                                     insertDossier
                             )) {

                    ps.setLong(1, patient.getId());
                    ps.setLong(2, medecinId);
                    ps.executeUpdate();
                }

                connection.commit();

                patient.setArchive(false);

                return patient;

            } catch (SQLException | RuntimeException e) {

                try {
                    connection.rollback();
                } catch (SQLException rollbackError) {
                    e.addSuppressed(rollbackError);
                }

                throw e;
            }
        }
    }

    @Override
    public boolean update(Patient patient)
            throws SQLException {

        String sql = """
            UPDATE patient
            SET nom = ?,
                prenom = ?,
                date_naissance = ?,
                sexe = ?,
                cin = ?,
                passeport = ?,
                email = ?,
                telephone = ?,
                adresse = ?,
                assurance = ?
            WHERE id = ?
              AND archive = FALSE
            """;

        try (Connection connection = factory.getConnection();
             PreparedStatement ps =
                     connection.prepareStatement(sql)) {

            ps.setString(1, patient.getNom());
            ps.setString(2, patient.getPrenom());
            ps.setDate(3, Date.valueOf(
                    patient.getDateNaissance()
            ));
            ps.setString(4, patient.getSexe());
            ps.setString(5, patient.getCin());
            ps.setString(6, patient.getPasseport());
            ps.setString(7, patient.getEmail());
            ps.setString(8, patient.getTelephone());
            ps.setString(9, patient.getAdresse());
            ps.setString(10, patient.getAssurance());
            ps.setLong(11, patient.getId());

            return ps.executeUpdate() > 0;
        }
    }

    @Override
    public boolean archive(Long id)
            throws SQLException {

        String sql = """
            UPDATE patient
            SET archive = TRUE
            WHERE id = ?
              AND archive = FALSE
            """;

        try (Connection connection = factory.getConnection();
             PreparedStatement ps =
                     connection.prepareStatement(sql)) {

            ps.setLong(1, id);

            return ps.executeUpdate() > 0;
        }
    }
}
