
package ma.dentalsoft.ui.view;

import ma.dentalsoft.ui.model.PatientTableModel;

import javax.swing.*;
import java.awt.*;

public class PatientView extends JFrame {

    public final JTextField txtNom = new JTextField();
    public final JTextField txtPrenom = new JTextField();
    public final JTextField txtNaissance = new JTextField();
    public final JTextField txtSexe = new JTextField();
    public final JTextField txtCin = new JTextField();
    public final JTextField txtPasseport = new JTextField();
    public final JTextField txtTelephone = new JTextField();
    public final JTextField txtEmail = new JTextField();
    public final JTextField txtAdresse = new JTextField();
    public final JTextField txtAssurance = new JTextField();

    public final JTextField txtRecherche =
            new JTextField(20);

    public final JButton btnAjouter =
            new JButton("Ajouter");

    public final JButton btnModifier =
            new JButton("Modifier");

    public final JButton btnArchiver =
            new JButton("Archiver");

    public final JButton btnRechercher =
            new JButton("Rechercher");

    public final JButton btnActualiser =
            new JButton("Actualiser");

    public final JButton btnVider =
            new JButton("Vider");

    public final PatientTableModel tableModel =
            new PatientTableModel();

    public final JTable table =
            new JTable(tableModel);

    public PatientView() {

        setTitle("DentalSoft - Gestion des patients");
        setSize(1100, 750);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel principal = new JPanel(
                new BorderLayout(10, 10)
        );

        principal.setBorder(
                BorderFactory.createEmptyBorder(
                        15, 15, 15, 15
                )
        );

        JLabel titre = new JLabel(
                "YOU SMILE - Gestion des patients"
        );

        titre.setFont(new Font(
                "Arial", Font.BOLD, 22
        ));

        principal.add(titre, BorderLayout.NORTH);

        JPanel centre = new JPanel(
                new BorderLayout(10, 10)
        );

        // Formulaire
        JPanel formulaire = new JPanel(
                new GridLayout(10, 2, 8, 8)
        );

        ajouterChamp(formulaire, "Nom :", txtNom);
        ajouterChamp(formulaire, "Prenom :", txtPrenom);
        ajouterChamp(
                formulaire,
                "Naissance (AAAA-MM-JJ) :",
                txtNaissance
        );
        ajouterChamp(formulaire, "Sexe :", txtSexe);
        ajouterChamp(formulaire, "CIN :", txtCin);
        ajouterChamp(
                formulaire, "Passeport :", txtPasseport
        );
        ajouterChamp(
                formulaire, "Telephone :", txtTelephone
        );
        ajouterChamp(formulaire, "Email :", txtEmail);
        ajouterChamp(
                formulaire, "Adresse :", txtAdresse
        );
        ajouterChamp(
                formulaire, "Assurance :", txtAssurance
        );

        JPanel actions = new JPanel(
                new FlowLayout(FlowLayout.LEFT)
        );

        actions.add(btnAjouter);
        actions.add(btnModifier);
        actions.add(btnArchiver);
        actions.add(btnVider);

        JPanel haut = new JPanel(
                new BorderLayout(5, 5)
        );

        haut.setBorder(
                BorderFactory.createTitledBorder(
                        "Informations du patient"
                )
        );

        haut.add(formulaire, BorderLayout.CENTER);
        haut.add(actions, BorderLayout.SOUTH);

        centre.add(haut, BorderLayout.NORTH);

        // Recherche
        JPanel recherche = new JPanel(
                new FlowLayout(FlowLayout.LEFT)
        );

        recherche.add(new JLabel("Rechercher par nom :"));
        recherche.add(txtRecherche);
        recherche.add(btnRechercher);
        recherche.add(btnActualiser);

        // Tableau
        table.setSelectionMode(
                ListSelectionModel.SINGLE_SELECTION
        );

        table.setRowHeight(25);
        table.setAutoCreateRowSorter(true);

        JPanel tableauPanel = new JPanel(
                new BorderLayout(5, 5)
        );

        tableauPanel.add(
                recherche, BorderLayout.NORTH
        );

        tableauPanel.add(
                new JScrollPane(table),
                BorderLayout.CENTER
        );

        centre.add(
                tableauPanel, BorderLayout.CENTER
        );

        principal.add(
                centre, BorderLayout.CENTER
        );

        setContentPane(principal);
    }

    private void ajouterChamp(
            JPanel panel,
            String label,
            JTextField field) {

        panel.add(new JLabel(label));
        panel.add(field);
    }

    public void viderFormulaire() {

        txtNom.setText("");
        txtPrenom.setText("");
        txtNaissance.setText("");
        txtSexe.setText("");
        txtCin.setText("");
        txtPasseport.setText("");
        txtTelephone.setText("");
        txtEmail.setText("");
        txtAdresse.setText("");
        txtAssurance.setText("");

        table.clearSelection();
    }

    public void afficherMessage(String message) {
        JOptionPane.showMessageDialog(
                this, message
        );
    }

    public void afficherErreur(String message) {
        JOptionPane.showMessageDialog(
                this,
                message,
                "Erreur",
                JOptionPane.ERROR_MESSAGE
        );
    }
}
