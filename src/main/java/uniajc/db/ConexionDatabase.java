/**
 * Clase para gestionar la conexión a la base de datos MySQL usando un archivo de propiedades.
 * permite crear y cerrar la conexión de manera segura.
 */
package uniajc.db;

// Importaciones necesarias
import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.SQLException;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Clase para gestionar la conexión a la base de datos MySQL usando un archivo
 * de propiedades.
 * permite crear y cerrar la conexión de manera segura.
 */
public class ConexionDatabase {
    // conexion unica (singleton)
    public static Connection connection = null;

    /**
     * Obtiene la conexión a la base de datos. Si no existe, la crea usando el
     * config.properties.
     * 
     * @return La conexión a la base de datos.
     * @throws SQLException si ocurre un error al conectar.
     */
    public static Connection getConnection() throws SQLException {
        if (connection == null || connection.isClosed()) {
            Properties properties = new Properties();

            // Cargar el archivo de propiedades desde resources
            try (InputStream input = ConexionDatabase.class.getClassLoader().getResourceAsStream("config.properties")) {

                if (input == null) {
                    throw new FileNotFoundException("Archivo config.properties no encontrado en resources");
                }

                properties.load(input);

                // Obtener los valores de las propiedades
                String url = properties.getProperty("database.url");
                String user = properties.getProperty("database.user");
                String password = properties.getProperty("database.password");

                // Establecer la conexión
                connection = DriverManager.getConnection(url, user, password);
                System.out.println("Conexión exitosa a la base de datos.");

            } catch (IOException e) {
                System.err.println("Error al leer config.properties: " + e.getMessage());
                throw new SQLException("No se pudo cargar la configuración de la base de datos.", e);
            } catch (SQLException e) {
                System.err.println("Error al conectar a la base de datos: " + e.getMessage());
                throw e;
            }

        }
        return connection;
    }

    /**
     * Cierra la conexión a la base de datos si está abierta.
     */
    public static void closeConnection() {
        if (connection != null) {
            try {
                if (!connection.isClosed()) {
                    connection.close();
                    System.out.println("Conexión cerrada correctamente.");
                }
            } catch (SQLException e) {
                System.err.println("Error al cerrar la conexión: " + e.getMessage());
            }
        }
    }

}
