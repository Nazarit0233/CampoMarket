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
    public Hyperlink linkIniciarSesion;

    // Campos específicos (creados dinámicamente)
    @FXML
    private TextField txtComprobante;
    @FXML
    private TextField txtFechaNacimiento;
    @FXML
    private TextField txtNivelAcceso;
    @FXML
    private TextField txtAreaResponsable;
    @FXML
    private TextField txtTurnoTrabajo;
    @FXML
    private TextField txtTipoVehiculo;
    @FXML
    private TextField txtlicenciaConduccion;
    @FXML
    private TextField txtplacaVehiculo;
    @FXML
    private TextField txtcajaAsignada;
    @FXML
    private TextField txtformaPago;
    @FXML
    private TextField txttotalRecaudado;

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
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/vista/VistaLogin.fxml"));
            Scene scene = new Scene(loader.load());

            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.centerOnScreen();
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
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
                public String toString(Rol rol) {
                    return rol == null ? "" : rol.getNombre();
                }

                @Override
                public Rol fromString(String string) {
                    // Not used
                    return null;
                }
            });
            // No default selection: user must choose a role explicitly
        }
    }

    @FXML
    private void mostrarCamposPorRol() {
        if (boxCamposRol == null)
            return;
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
                txtNivelAcceso = new TextField();
                txtNivelAcceso.setPromptText("Nivel de acceso");
                txtAreaResponsable = new TextField();
                txtAreaResponsable.setPromptText("Área responsable");
                boxCamposRol.getChildren().addAll(txtNivelAcceso, txtAreaResponsable);
                break;

            case "Repartidor":
                txtTipoVehiculo = new TextField();
                txtTipoVehiculo.setPromptText("Tipo de vehículo");
                txtlicenciaConduccion = new TextField();
                txtlicenciaConduccion.setPromptText("Licencia de conducción");
                txtplacaVehiculo = new TextField();
                txtplacaVehiculo.setPromptText("Placa del vehiculo");
                txtTurnoTrabajo = new TextField();
                txtTurnoTrabajo.setPromptText("Turno de trabajo");
                boxCamposRol.getChildren().addAll(txtTipoVehiculo, txtlicenciaConduccion, txtplacaVehiculo,
                        txtTurnoTrabajo);
                break;

            case "Cajero":
                txtTurnoTrabajo = new TextField();
                txtTurnoTrabajo.setPromptText("Turno de trabajo");
                txtcajaAsignada = new TextField();
                txtcajaAsignada.setPromptText("Caja asignada");
                txtformaPago = new TextField();
                txtformaPago.setPromptText("Metodo de pago");
                boxCamposRol.getChildren().addAll(txtTurnoTrabajo, txtcajaAsignada, txtformaPago);
                break;
            case "Despachador":
                txtAreaResponsable = new TextField();
                txtAreaResponsable.setPromptText("Área responsable");
                txtTurnoTrabajo = new TextField();
                txtTurnoTrabajo.setPromptText("Turno de trabajo");
                boxCamposRol.getChildren().addAll(txtAreaResponsable, txtTurnoTrabajo);
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
                    cuenta.setFecha_Nacimiento(txtFechaNacimiento.getText().trim());
                    break;
                case "Administrador":
                    cuenta.setNivel_Acceso(txtNivelAcceso.getText().trim());
                    cuenta.setArea_Responsable(txtAreaResponsable.getText().trim());
                    break;
                case "Repartidor":
                    cuenta.setTipo_Vehiculo(txtTipoVehiculo.getText().trim());
                    cuenta.setLicencia_Conducion(txtlicenciaConduccion.getText().trim());
                    cuenta.setPlaca_Vehiculo(txtplacaVehiculo.getText().trim());
                    cuenta.setTurno_Trabajo(txtTurnoTrabajo.getText().trim());
                    break;
                case "Cajero":
                    cuenta.setTurno_Trabajo(txtTurnoTrabajo.getText().trim());
                    try {
                        cuenta.setCaja_Asignada(Integer.parseInt(txtcajaAsignada.getText().trim()));
                    } catch (NumberFormatException e) {
                        System.out.println("Error: la caja asignada debe ser un número.");
                    }
                    cuenta.setForma_Pago(txtformaPago.getText().trim());
                    break;
                case "Despachador":
                    cuenta.setArea_Responsable(txtAreaResponsable.getText().trim());
                    cuenta.setTurno_Trabajo(txtTurnoTrabajo.getText().trim());
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
        if (boxCamposRol != null)
            boxCamposRol.getChildren().clear();
        if (cmbRol != null)
            cmbRol.getSelectionModel().clearSelection();
    }
}
