package uniajc.controlador;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import uniajc.dao.CuentaDAO;
import uniajc.db.ConexionDatabase;
import uniajc.modelo.Cuenta;
import uniajc.Roles.*;

import java.io.IOException;
import java.sql.Connection;
import java.util.Map;

import javax.swing.JOptionPane;

public class LoginControladorVista {

    @FXML private TextField txtCorreo;
    @FXML private PasswordField txtContrasena;
    @FXML private Label lblMensaje;
    @FXML private Hyperlink linkCrearCuenta;

    private CuentaDAO cuentaDAO;

    public LoginControladorVista() {
        try {
            Connection conexion = ConexionDatabase.getConnection();
            cuentaDAO = new CuentaDAO(conexion);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void abrirCrearCuenta(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/uniajc/vistas/VistaCrearCuenta.fxml"));
            Scene scene = new Scene(loader.load());

            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.centerOnScreen();
            stage.show();

        } catch (IOException e) {
            
            JOptionPane.showMessageDialog(null,"Error al cargar la vista de Crear Cuenta." + e.getMessage());
        }
    }

    @FXML
    private void iniciarSesion(ActionEvent event) {
        String correo = txtCorreo.getText().trim();
        String contrasena = txtContrasena.getText().trim();

        if (correo.isEmpty() || contrasena.isEmpty()) {
            lblMensaje.setText("Debe ingresar correo y contraseña.");
            lblMensaje.setStyle("-fx-text-fill: red;");
            return;
        }

        Cuenta cuenta = cuentaDAO.loginCuenta(correo, contrasena);

        if (cuenta != null) {
            lblMensaje.setText("Inicio de sesión exitoso");
            lblMensaje.setStyle("-fx-text-fill: green;");

            // Llamar método para redirigir según rol
            redirigirSegunRol(cuenta.getRol(), event);
        } else {
            lblMensaje.setText("Credenciales incorrectas.");
            lblMensaje.setStyle("-fx-text-fill: red;");
        }
    }

    private void redirigirSegunRol(Rol rol, ActionEvent event) {
    var vistas = Map.of(
        RolAdministrador.class, "/uniajc/vistas/VistaAdministrador.fxml",
        RolCliente.class, "/uniajc/vistas/VistaCliente.fxml",
        RolCajero.class, "/uniajc/vistas/VistaCajero.fxml",
        RolRepartidor.class, "/uniajc/vistas/VistaRepartidor.fxml",
        RolDespachador.class, "/uniajc/vistas/VistaDespachador.fxml"
    );

    String vista = vistas.get(rol.getClass());

        if (vista != null) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource(vista));
                Scene scene = new Scene(loader.load());

                Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                stage.setScene(scene);
                stage.centerOnScreen();
                stage.show();

            } catch (IOException e) {
                e.printStackTrace();
                lblMensaje.setText("Error al cargar la vista del rol.");
            }
        } else {
        lblMensaje.setText("Rol no tiene vista asignada.");
    }
}
}