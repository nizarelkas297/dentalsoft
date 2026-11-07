
package ma.dentalsoft;

import ma.dentalsoft.config.ApplicationContext;
import ma.dentalsoft.service.PatientService;
import ma.dentalsoft.ui.controller.PatientController;
import ma.dentalsoft.ui.view.PatientView;

import javax.swing.*;

public class DentalSoftApplication {

    public static void main(String[] args) {

        SwingUtilities.invokeLater(() -> {

            try {
                // Initialisation du service
                PatientService service =
                        ApplicationContext.getPatientService();

                // Creation de la fenetre
                PatientView view = new PatientView();

                // Association Controller + View + Service
                new PatientController(view, service);

                // Affichage
                view.setVisible(true);

            } catch (Exception e) {
                e.printStackTrace();

                JOptionPane.showMessageDialog(
                        null,
                        "Erreur : " + e.getMessage(),
                        "DentalSoft",
                        JOptionPane.ERROR_MESSAGE
                );
            }
        });
    }
}
