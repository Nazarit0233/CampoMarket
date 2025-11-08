package uniajc.controlador;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.scene.Node;
import javafx.stage.Stage;
import javafx.event.ActionEvent;
import javafx.util.StringConverter;
import uniajc.modelo.Cuenta;
import uniajc.Roles.*;
import uniajc.db.*;

import java.io.IOException;
import java.sql.Connection;

import javax.swing.JOptionPane;

public class CuentaControladorVista {

    @FXML
    private TextField txtNombre;
    @FXML
    private TextField txtCorreo;
    @FXML
    private PasswordField txtContrasena;
    @FXML
    private PasswordField txtConfirmar;
    @FXML
    private TextField txtTelefono;
    @FXML
    private ComboBox<Rol> cmbRol;
    @FXML
    private Label lblMensaje;
    @FXML
    private Label lblRolSeleccionado;
    @FXML
    private VBox boxCamposRol;
    @FXML
    private Hyperlink linkIniciarSesion;

    // Campos específicos (creados dinámicamente)
    private TextField txtComprobante;
    private TextField txtFechaNacimiento;
    private TextField txtAreaResponsable;
    private TextField txtTipoVehiculo;
    private TextField txtTurnoTrabajo;

    private CuentaControlador cuentaControlador;

    // Se inicializa la conexión con la base de datos
    public CuentaControladorVista() {
        try {
            Connection conexion = ConexionDatabase.getConnection();
            this.cuentaControlador = new CuentaControlador(conexion);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void abrirLogin(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/uniajc/vistas/VistaLogin.fxml"));
            Scene scene = new Scene(loader.load());

            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.centerOnScreen();
            stage.show();

        } catch (IOException e) {

            JOptionPane.showMessageDialog(null, "Error al cargar la vista de login." + e.getMessage());
        }
    }

    @FXML
    private void initialize() {
        lblMensaje.setText("");
        // Poblar combo de roles
        if (cmbRol != null) {
            cmbRol.getItems().clear();
            cmbRol.getItems().addAll(new RolAdministrador(), new RolCajero(), new RolCliente(), new RolRepartidor(),
                    new RolDespachador());
            cmbRol.setConverter(new StringConverter<Rol>() {
                @Override
                public String toString(Rol role) {
                    return role == null ? "" : role.getNombre();
                }

                @Override
                public Rol fromString(String string) {
                    // Not used
                    return null;
                }
            });
            // No default selection: user must choose a role explicitly
        }
        cmbRol.setOnAction(e -> mostrarCamposPorRol());
    }

    private void mostrarCamposPorRol() {
        boxCamposRol.getChildren().clear();
        Rol rol = cmbRol.getValue();
        if (rol == null)
            return;

        switch (rol.getNombre()) {
            case "Cliente":
                txtComprobante = new TextField();
                txtComprobante.setPromptText("Comprobante de identidad");
                txtFechaNacimiento = new TextField();
                txtFechaNacimiento.setPromptText("Fecha de nacimiento");
                boxCamposRol.getChildren().addAll(txtComprobante, txtFechaNacimiento);
                break;

            case "Administrador":
                txtAreaResponsable = new TextField();
                txtAreaResponsable.setPromptText("Área responsable");
                boxCamposRol.getChildren().add(txtAreaResponsable);
                break;

            case "Repartidor":
                txtTipoVehiculo = new TextField();
                txtTipoVehiculo.setPromptText("Tipo de vehículo");
                txtTurnoTrabajo = new TextField();
                txtTurnoTrabajo.setPromptText("Turno de trabajo");
                boxCamposRol.getChildren().addAll(txtTipoVehiculo, txtTurnoTrabajo);
                break;

            case "Cajero":
            case "Despachador":
                txtTurnoTrabajo = new TextField();
                txtTurnoTrabajo.setPromptText("Turno de trabajo");
                boxCamposRol.getChildren().add(txtTurnoTrabajo);
                break;
        }
    }

    @FXML
    private void crearCuenta(ActionEvent event) {
        String nombre = txtNombre.getText().trim();
        String correo = txtCorreo.getText().trim();
        String contrasena = txtContrasena.getText().trim();
        String confirmar = txtConfirmar.getText().trim();
        String telefono = txtTelefono.getText().trim();

        if (nombre.isEmpty() || correo.isEmpty() || contrasena.isEmpty() || telefono.isEmpty()) {
            lblMensaje.setText(" Todos los campos son obligatorios.");
            lblMensaje.setStyle("-fx-text-fill: red;");
            return;
        }

        if (!contrasena.equals(confirmar)) {
            lblMensaje.setText(" Las contraseñas no coinciden.");
            lblMensaje.setStyle("-fx-text-fill: red;");
            return;
        }

        try {
            Rol rolSeleccionado = (cmbRol != null && cmbRol.getValue() != null) ? cmbRol.getValue() : null;
            if (rolSeleccionado == null) {
                lblMensaje.setText("Seleccione un rol.");
                lblMensaje.setStyle("-fx-text-fill: red;");
                return;
            }
            Cuenta cuenta = new Cuenta(rolSeleccionado, nombre, correo, contrasena, telefono);

            // Campos específicos según rol
            switch (rolSeleccionado.getNombre()) {
                case "Cliente":
                    cuenta.setComprobanteIdentidad(txtComprobante.getText().trim());
                    cuenta.setFechaNacimiento(txtFechaNacimiento.getText().trim());
                    break;
                case "Administrador":
                    cuenta.setAreaResponsable(txtAreaResponsable.getText().trim());
                    break;
                case "Repartidor":
                    cuenta.setTipoVehiculo(txtTipoVehiculo.getText().trim());
                    cuenta.setTurnoTrabajo(txtTurnoTrabajo.getText().trim());
                    break;
                case "Cajero":
                    cuenta.setTurnoTrabajo(txtTurnoTrabajo.getText().trim());
                case "Despachador":
                    cuenta.setTurnoTrabajo(txtTurnoTrabajo.getText().trim());
                    break;
            }

            boolean ok = cuentaControlador.registrarCuenta(cuenta);

            if (ok) {
                lblMensaje.setText(" Cuenta creada exitosamente.");
                lblMensaje.setStyle("-fx-text-fill: green;");
                if (lblRolSeleccionado != null)
                    lblRolSeleccionado.setText("Rol asignado: " + rolSeleccionado.getNombre());
                limpiarCampos();
            } else {
                lblMensaje.setText(" No se pudo crear la cuenta.");
                lblMensaje.setStyle("-fx-text-fill: red;");
            }
        } catch (Exception e) {
            lblMensaje.setText(" Error: " + e.getMessage());
            lblMensaje.setStyle("-fx-text-fill: red;");
        }
    }

    private void limpiarCampos() {
        txtNombre.clear();
        txtCorreo.clear();
        txtContrasena.clear();
        txtConfirmar.clear();

        txtTelefono.clear();
    }
}
