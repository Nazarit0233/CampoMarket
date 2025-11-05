package uniajc.controlador;

// Importaciones necesarias
import uniajc.dao.CuentaDAO;
import uniajc.modelo.Cuenta;
import javax.swing.*;
import java.sql.*;
import java.util.List;

public class CuentaControlador {
    private CuentaDAO dao;

    // Constructor del controlador que recibe la conexión a la base de datos
    public CuentaControlador(Connection conexion) {
        this.dao = new CuentaDAO(conexion);
    }

    // Métodos para gestionar cuentas
    // Crear
    public void registrarCuenta(Cuenta cuenta) throws SQLException {
        try {
            dao.registrarCuenta(cuenta);
            JOptionPane.showMessageDialog(null, "Cuenta registrada exitosamente.");
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al registrar la cuenta: " + e.getMessage());
        }
    }

    // Actualizar
    public void actualizarCuenta(Cuenta cuenta) throws SQLException {
        try {
            dao.actualizarCuenta(cuenta);
            JOptionPane.showInternalMessageDialog(null, "Cuenta actualizado exitosamente.");
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null,"Error al actualizar la cuenta: " + e.getMessage());
        }
    }

    // Eliminar
    public void eliminarCuenta(int id) throws SQLException {
        try {
            dao.eliminarCuenta(id);
            JOptionPane.showMessageDialog(null, "Cuenta eliminada exitosamente.");
        } catch (SQLException e) {
            JOptionPane.showInternalMessageDialog(null, "Error al eliminar la cuenta: " + e.getMessage());
        }
    }

    // Mostrar
    public List<Cuenta> listarCuentas() throws SQLException {
        try {
            return dao.listarCuentas();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null,"Error al mostrar las cuentas: " + e.getMessage());
            return null;
        }
    }

    // Buscar
    public void buscarPorId(int id) throws SQLException {
        try {
            dao.buscarPorId(id);
            JOptionPane.showMessageDialog(null, "Cuenta encontrada exitosamente.");
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al buscar la cuenta: " + e.getMessage());
        }
    }

    

}
