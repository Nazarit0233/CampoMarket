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

import javax.swing.JOptionPane;

/**
 * Clase para gestionar la conexión a la base de datos MySQL usando un archivo de propiedades.
 * permite crear y cerrar la conexión de manera segura.
 */
public class ConexionDatabase {
    //conexion unica (singleton)
    public static Connection connetion = null;

    /**
     * Obtiene la conexión a la base de datos. Si no existe, la crea usando el config.properties.
     * @return La conexión a la base de datos.
     */
    public static Connection getConnection() throws SQLException {

        Properties properties = new Properties();

        if (connetion == null) {
            try {

                // Cargar el archivo de propiedades desde resources
                InputStream input = ConexionDatabase.class.getClassLoader().getResourceAsStream("config.properties");
            if (input == null) {
                throw new FileNotFoundException("Archivo config.properties no encontrado en resources");
            }
            properties.load(input);

                // Obtener los valores de las propiedades
                String url = properties.getProperty("database.url");
                String user = properties.getProperty("database.user");
                String password = properties.getProperty("database.password");

                // Establecer la conexión
                connetion = DriverManager.getConnection(url, user, password);
                JOptionPane.showMessageDialog(null, "Conexion exitosa a la base de datos.");
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(null, "Error al conectar a la base de datos: " + e.getMessage());
            } catch (FileNotFoundException e) {
                JOptionPane.showMessageDialog(null, "Archivo de configuracion no encontrado: " + e.getMessage());
            } catch (IOException e) {
                JOptionPane.showMessageDialog(null, "Error al leer el archivo de configuracion: " + e.getMessage());
            }

        }
        return connetion;
    }

    /**
     * Cierra la conexión a la base de datos si está abierta.
     */
    public static void closeConnection() {
        if (connetion != null) {
            try {
                connetion.close();
                JOptionPane.showMessageDialog(null, "Conexion cerrada correctamente.");
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(null, "Error al cerrar la conexion: " + e.getMessage());
            }
        }
    }

}
