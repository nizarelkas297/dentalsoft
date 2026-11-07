
package ma.dentalsoft.ui.controller;

import ma.dentalsoft.domain.Patient;
import ma.dentalsoft.service.PatientService;
import ma.dentalsoft.ui.view.PatientView;

import javax.swing.*;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;

public class PatientController {

    private final PatientView view;
    private final PatientService service;

    // Medecin de test : dr.doe (ID 2)
    private static final Long MEDECIN_ID = 2L;

    public PatientController(
            PatientView view,
            PatientService service) {

        this.view = view;
        this.service = service;

        initialiserActions();
        actualiser();
    }

    private void initialiserActions() {

        view.btnAjouter.addActionListener(
                e -> ajouter()
        );

        view.btnModifier.addActionListener(
                e -> modifier()
        );

        view.btnArchiver.addActionListener(
                e -> archiver()
        );

        view.btnRechercher.addActionListener(
                e -> rechercher()
        );

        view.btnActualiser.addActionListener(
                e -> actualiser()
        );

        view.btnVider.addActionListener(
                e -> view.viderFormulaire()
        );

        view.table.getSelectionModel()
                .addListSelectionListener(e -> {

                    if (!e.getValueIsAdjusting()) {
                        remplirFormulaire();
                    }
                });
    }

    private Patient lireFormulaire() {

        Patient patient = new Patient();

        patient.setNom(
                view.txtNom.getText().trim()
        );

        patient.setPrenom(
                view.txtPrenom.getText().trim()
        );

        try {
            patient.setDateNaissance(
                    LocalDate.parse(
                            view.txtNaissance.getText().trim()
                    )
            );
        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException(
                    "Date invalide. Format : AAAA-MM-JJ"
            );
        }

        patient.setSexe(
                valeurOuNull(view.txtSexe.getText())
        );

        patient.setCin(
                valeurOuNull(view.txtCin.getText())
        );

        patient.setPasseport(
                valeurOuNull(view.txtPasseport.getText())
        );

        patient.setTelephone(
                valeurOuNull(view.txtTelephone.getText())
        );

        patient.setEmail(
                valeurOuNull(view.txtEmail.getText())
        );

        patient.setAdresse(
                valeurOuNull(view.txtAdresse.getText())
        );

        patient.setAssurance(
                valeurOuNull(view.txtAssurance.getText())
        );

        return patient;
    }

    private String valeurOuNull(String value) {
        String texte = value.trim();
        return texte.isEmpty() ? null : texte;
    }

    private Patient patientSelectionne() {

        int row = view.table.getSelectedRow();

        if (row == -1) {
            return null;
        }

        int modelRow =
                view.table.convertRowIndexToModel(row);

        return view.tableModel.getPatientAt(modelRow);
    }

    private void remplirFormulaire() {

        Patient p = patientSelectionne();

        if (p == null) {
            return;
        }

        view.txtNom.setText(p.getNom());
        view.txtPrenom.setText(p.getPrenom());

        view.txtNaissance.setText(
                p.getDateNaissance() == null
                        ? "" : p.getDateNaissance().toString()
        );

        view.txtSexe.setText(
                texte(p.getSexe())
        );

        view.txtCin.setText(
                texte(p.getCin())
        );

        view.txtPasseport.setText(
                texte(p.getPasseport())
        );

        view.txtTelephone.setText(
                texte(p.getTelephone())
        );

        view.txtEmail.setText(
                texte(p.getEmail())
        );

        view.txtAdresse.setText(
                texte(p.getAdresse())
        );

        view.txtAssurance.setText(
                texte(p.getAssurance())
        );
    }

    private String texte(String value) {
        return value == null ? "" : value;
    }

    private void ajouter() {

        try {
            Patient p = lireFormulaire();

            service.addPatient(p, MEDECIN_ID);

            view.afficherMessage(
                    "Patient ajoute avec succes !"
            );

            actualiser();
            view.viderFormulaire();

        } catch (Exception e) {
            view.afficherErreur(e.getMessage());
        }
    }

    private void modifier() {

        Patient selection = patientSelectionne();

        if (selection == null) {
            view.afficherErreur(
                    "Selectionnez un patient."
            );
            return;
        }

        if (selection.isArchive()) {
            view.afficherErreur(
                    "Un patient archive ne peut pas etre modifie."
            );
            return;
        }

        try {
            Patient p = lireFormulaire();
            p.setId(selection.getId());

            boolean ok = service.updatePatient(p);

            if (ok) {
                view.afficherMessage(
                        "Patient modifie avec succes !"
                );
            } else {
                view.afficherErreur(
                        "Modification impossible."
                );
            }

            actualiser();
            view.viderFormulaire();

        } catch (Exception e) {
            view.afficherErreur(e.getMessage());
        }
    }

    private void archiver() {

        Patient p = patientSelectionne();

        if (p == null) {
            view.afficherErreur(
                    "Selectionnez un patient."
            );
            return;
        }

        int confirmation = JOptionPane.showConfirmDialog(
                view,
                "Archiver le patient " + p.getNom() + " ?",
                "Confirmation",
                JOptionPane.YES_NO_OPTION
        );

        if (confirmation != JOptionPane.YES_OPTION) {
            return;
        }

        try {
            boolean ok =
                    service.archivePatient(p.getId());

            if (ok) {
                view.afficherMessage(
                        "Patient archive avec succes !"
                );
            } else {
                view.afficherErreur(
                        "Archivage impossible."
                );
            }

            actualiser();
            view.viderFormulaire();

        } catch (Exception e) {
            view.afficherErreur(e.getMessage());
        }
    }

    private void rechercher() {

        try {
            String nom =
                    view.txtRecherche.getText().trim();

            view.tableModel.setPatients(
                    service.searchPatients(nom)
            );

        } catch (Exception e) {
            view.afficherErreur(e.getMessage());
        }
    }

    private void actualiser() {

        try {
            view.tableModel.setPatients(
                    service.getAllPatients()
            );

        } catch (Exception e) {
            view.afficherErreur(e.getMessage());
        }
    }
}
