
package ma.dentalsoft.shared;

import ma.dentalsoft.domain.Patient;
import ma.dentalsoft.domain.Utilisateur;
import ma.dentalsoft.domain.Role;
import ma.dentalsoft.domain.DossierMedical;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Date;
import java.sql.Timestamp;

public final class RowMappers {

    private RowMappers() {
    }

    public static Patient toPatient(ResultSet rs)
            throws SQLException {

        Patient patient = new Patient();

        patient.setId(rs.getLong("id"));
        patient.setNom(rs.getString("nom"));
        patient.setPrenom(rs.getString("prenom"));

        Date naissance = rs.getDate("date_naissance");

        if (naissance != null) {
            patient.setDateNaissance(naissance.toLocalDate());
        }

        patient.setSexe(rs.getString("sexe"));
        patient.setCin(rs.getString("cin"));
        patient.setPasseport(rs.getString("passeport"));
        patient.setEmail(rs.getString("email"));
        patient.setTelephone(rs.getString("telephone"));
        patient.setAdresse(rs.getString("adresse"));
        patient.setAssurance(rs.getString("assurance"));
        patient.setArchive(rs.getBoolean("archive"));

        Timestamp created = rs.getTimestamp("created_at");

        if (created != null) {
            patient.setCreatedAt(created.toLocalDateTime());
        }

        return patient;
    }

    public static Utilisateur toUtilisateur(ResultSet rs)
            throws SQLException {

        Utilisateur utilisateur = new Utilisateur();

        utilisateur.setId(rs.getLong("id"));
        utilisateur.setLogin(rs.getString("login"));
        utilisateur.setPasswordHash(
                rs.getString("password_hash")
        );
        utilisateur.setNom(rs.getString("nom"));
        utilisateur.setPrenom(rs.getString("prenom"));
        utilisateur.setEmail(rs.getString("email"));
        utilisateur.setTelephone(rs.getString("telephone"));
        utilisateur.setAdresse(rs.getString("adresse"));
        utilisateur.setSpecialite(rs.getString("specialite"));
        utilisateur.setActif(rs.getBoolean("actif"));

        Timestamp created = rs.getTimestamp("created_at");

        if (created != null) {
            utilisateur.setCreatedAt(
                    created.toLocalDateTime()
            );
        }

        return utilisateur;
    }

    public static Role toRole(ResultSet rs)
            throws SQLException {

        Role role = new Role();

        role.setCode(rs.getString("code"));
        role.setLibelle(rs.getString("libelle"));

        return role;
    }

    public static DossierMedical toDossierMedical(
            ResultSet rs) throws SQLException {

        DossierMedical dossier = new DossierMedical();

        dossier.setId(rs.getLong("id"));
        dossier.setPatientId(rs.getLong("patient_id"));
        dossier.setMedecinId(rs.getLong("medecin_id"));

        Timestamp date = rs.getTimestamp("date_creation");

        if (date != null) {
            dossier.setDateCreation(date.toLocalDateTime());
        }

        return dossier;
    }
}
