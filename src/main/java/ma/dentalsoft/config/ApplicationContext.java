
package ma.dentalsoft.config;

import ma.dentalsoft.repository.PatientRepository;
import ma.dentalsoft.service.PatientService;

import java.io.InputStream;
import java.util.Properties;

public final class ApplicationContext {

    private static final Properties properties =
            new Properties();

    static {
        try (InputStream input =
                     ApplicationContext.class
                             .getClassLoader()
                             .getResourceAsStream(
                                     "application.properties"
                             )) {

            if (input == null) {
                throw new IllegalStateException(
                        "application.properties introuvable"
                );
            }

            properties.load(input);

        } catch (Exception e) {
            throw new IllegalStateException(
                    "Erreur de chargement de configuration", e
            );
        }
    }

    private ApplicationContext() {
    }

    public static PatientRepository getPatientRepository() {

        String className = properties.getProperty(
                "repository.patient"
        );

        if (className == null || className.isBlank()) {
            throw new IllegalStateException(
                    "repository.patient non configure"
            );
        }

        try {
            Class<?> clazz = Class.forName(className);

            Object instance = clazz
                    .getDeclaredConstructor()
                    .newInstance();

            return PatientRepository.class.cast(instance);

        } catch (Exception e) {
            throw new IllegalStateException(
                    "Erreur de creation du repository", e
            );
        }
    }

    public static PatientService getPatientService() {

        return new PatientService(
                getPatientRepository()
        );
    }
}
