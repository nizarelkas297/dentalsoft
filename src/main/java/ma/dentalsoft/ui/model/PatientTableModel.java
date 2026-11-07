
package ma.dentalsoft.ui.model;

import ma.dentalsoft.domain.Patient;

import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;
import java.util.List;

public class PatientTableModel extends AbstractTableModel {

    private final String[] colonnes = {
            "ID", "Nom", "Prenom", "Naissance",
            "Telephone", "Email", "Etat"
    };

    private List<Patient> patients = new ArrayList<>();

    public void setPatients(List<Patient> patients) {
        this.patients = new ArrayList<>(patients);
        fireTableDataChanged();
    }

    public Patient getPatientAt(int row) {
        return patients.get(row);
    }

    @Override
    public int getRowCount() {
        return patients.size();
    }

    @Override
    public int getColumnCount() {
        return colonnes.length;
    }

    @Override
    public String getColumnName(int column) {
        return colonnes[column];
    }

    @Override
    public Object getValueAt(int row, int column) {

        Patient p = patients.get(row);

        return switch (column) {
            case 0 -> p.getId();
            case 1 -> p.getNom();
            case 2 -> p.getPrenom();
            case 3 -> p.getDateNaissance();
            case 4 -> p.getTelephone();
            case 5 -> p.getEmail();
            case 6 -> p.isArchive()
                    ? "Archive" : "Actif";
            default -> "";
        };
    }
}
