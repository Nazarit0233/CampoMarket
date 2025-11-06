package uniajc.controlador;

// Importaciones necesarias
import uniajc.dao.*;
import uniajc.modelo.*;
import javafx.*;
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
    public boolean registrarCuenta(Cuenta cuenta) {
        try {
            boolean ok = dao.registrarCuenta(cuenta);
            if (ok) {
                JOptionPane.showMessageDialog(null, " Cuenta registrada exitosamente.");
            }
            return ok;
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, " Error al registrar la cuenta: " + e.getMessage());
            return false;
        }
    }
    // Actualizar
    public boolean actualizarCuenta(Cuenta cuenta) {
        try {
            boolean ok = dao.actualizarCuenta(cuenta);
            if (ok) {
                JOptionPane.showMessageDialog(null, " Cuenta actualizada exitosamente.");
            }
            return ok;
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, " Error al actualizar la cuenta: " + e.getMessage());
            return false;
        }
    }

    // Eliminar
    public boolean eliminarCuenta(int id) {
        try {
            boolean ok = dao.eliminarCuenta(id);
            if (ok) {
                JOptionPane.showMessageDialog(null, " Cuenta eliminada exitosamente.");
            }
            return ok;
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, " Error al eliminar la cuenta: " + e.getMessage());
            return false;
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
    public Cuenta buscarPorId(int id) {
        try {
            Cuenta cuenta = dao.buscarPorId(id);
            if (cuenta != null) {
                JOptionPane.showMessageDialog(null, " Cuenta encontrada.");
            } else {
                JOptionPane.showMessageDialog(null, " No se encontró la cuenta con ID: " + id);
            }
            return cuenta;
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, " Error al buscar la cuenta: " + e.getMessage());
            return null;
        }
    }

    

}
