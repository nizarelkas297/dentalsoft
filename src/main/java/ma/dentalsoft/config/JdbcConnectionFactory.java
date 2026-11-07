
package ma.dentalsoft.config;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public final class JdbcConnectionFactory {

    private static JdbcConnectionFactory instance;

    private final String url;
    private final String user;
    private final String password;

    private JdbcConnectionFactory() {

        Properties properties = new Properties();

        try (InputStream input = getClass()
                .getClassLoader()
                .getResourceAsStream("application.properties")) {

            if (input == null) {
                throw new IllegalStateException(
                        "Fichier application.properties introuvable"
                );
            }

            properties.load(input);

            url = properties.getProperty("datasource.url");
            user = properties.getProperty("datasource.user");
            password = properties.getProperty(
                    "datasource.password", ""
            );

            String driver = properties.getProperty(
                    "datasource.driver"
            );

            if (url == null || user == null ||
                    driver == null) {
                throw new IllegalStateException(
                        "Configuration JDBC incomplete"
                );
            }

            Class.forName(driver);

        } catch (Exception e) {
            throw new IllegalStateException(
                    "Erreur de configuration JDBC", e
            );
        }
    }

    public static synchronized
    JdbcConnectionFactory getInstance() {

        if (instance == null) {
            instance = new JdbcConnectionFactory();
        }

        return instance;
    }

    public Connection getConnection()
            throws SQLException {

        return DriverManager.getConnection(
                url, user, password
        );
    }
}
