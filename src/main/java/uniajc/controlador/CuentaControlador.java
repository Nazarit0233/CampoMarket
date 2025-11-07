package uniajc.controlador;

// Importaciones necesarias
import uniajc.dao.*;
import uniajc.modelo.*;
import uniajc.db.ConexionDatabase;
import uniajc.Roles.Rol;
import uniajc.Roles.RolCliente;
import uniajc.Roles.RolAdministrador;
import uniajc.Roles.RolCajero;
import uniajc.Roles.RolDespachador;
import uniajc.Roles.RolRepartidor;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javax.swing.*;
import java.sql.*;
import java.util.List;

public class CuentaControlador {
    private CuentaDAO dao;

    @FXML private TextField txtNombre;
    @FXML private TextField txtCorreo;
    @FXML private PasswordField txtContrasena;
    @FXML private PasswordField txtConfirmar;
    @FXML private TextField txtDireccion;
    @FXML private TextField txtTelefono;
    @FXML private ComboBox<String> cmbRol;
    @FXML private Button btnCrearCuenta;
    @FXML private Label lblMensaje;

    // Constructor por defecto requerido por FXMLLoader
    public CuentaControlador() {
        // No inicializamos la DAO aquí porque la llamada a la BD debe hacerse
        // en tiempo de ejecución (initialize) para que FXMLLoader pueda crear
        // la instancia sin parámetros.
    }

    // Constructor alternativo que recibe una conexión (puede usarse fuera de FXML)
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
    
    @FXML
    public void initialize() {
        // Inicializar la conexión y la DAO cuando el FXML haya sido cargado
        try {
            Connection conexion = ConexionDatabase.getConnection();
            if (conexion == null) {
                if (lblMensaje != null) lblMensaje.setText("Error de conexión a la base de datos.");
                if (btnCrearCuenta != null) btnCrearCuenta.setDisable(true);
                return;
            }
            this.dao = new CuentaDAO(conexion);
        } catch (Exception e) {
            // Mostrar mensaje y deshabilitar el botón si hay problema
            if (lblMensaje != null) lblMensaje.setText("Error al inicializar BD: " + e.getMessage());
            if (btnCrearCuenta != null) btnCrearCuenta.setDisable(true);
        }

        // Poblar ComboBox de roles
        if (cmbRol != null) {
            cmbRol.getItems().clear();
            cmbRol.getItems().addAll("Administrador", "Cajero", "Cliente", "Repartidor", "Despachador");
            cmbRol.setValue("Cliente");
        }

        // Configurar acción del botón crear (si está presente en el FXML)
        if (btnCrearCuenta != null) {
            btnCrearCuenta.setOnAction(ev -> {
                try {
                    String nombre = txtNombre.getText();
                    String correo = txtCorreo.getText();
                    String contrasena = txtContrasena.getText();
                    String confirmar = txtConfirmar.getText();
                    String telefono = txtTelefono.getText();

                    if (!contrasena.equals(confirmar)) {
                        if (lblMensaje != null) lblMensaje.setText("Las contraseñas no coinciden.");
                        return;
                    }

                    String rolNombre = (cmbRol != null) ? cmbRol.getValue() : "Cliente";
                    Rol rol = mapRol(rolNombre);
                    Cuenta cuenta = new Cuenta(rol, nombre, correo, contrasena, telefono);
                    boolean creado = registrarCuenta(cuenta);
                    if (creado) {
                        if (lblMensaje != null) lblMensaje.setText("Cuenta creada correctamente.");
                    } else {
                        if (lblMensaje != null) lblMensaje.setText("No se pudo crear la cuenta.");
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                    if (lblMensaje != null) lblMensaje.setText("Error: " + ex.getMessage());
                }
            });
        }
    }

    /**
     * Mapear el nombre del rol a una instancia de Rol concreta.
     */
    private Rol mapRol(String nombreRol) {
        if (nombreRol == null) return new RolCliente();
        switch (nombreRol) {
            case "Administrador":
                return new RolAdministrador();
            case "Cajero":
                return new RolCajero();
            case "Cliente":
                return new RolCliente();
            case "Repartidor":
                return new RolRepartidor();
            case "Despachador":
                return new RolDespachador();
            default:
                return new RolCliente();
        }
    }

}
