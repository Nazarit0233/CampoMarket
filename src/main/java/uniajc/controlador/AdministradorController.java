package uniajc.controlador;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

import java.io.IOException;

public class AdministradorController {

    // 🔹 Labels de la parte inferior (resumen)
    @FXML
    private Label lblPedidosHoy;

    @FXML
    private Label lblVentasDia;

    @FXML
    private Label lblRepartidoresActivos;

    @FXML
    private Label lblProductoMasVendido;

    // 🔹 Label con el nombre del administrador
    @FXML
    private Label lblNombreAdmin;

    // 🔹 Botones del menú
    @FXML
    private Button btnInicio;

    @FXML
    private Button btnUsuarios;

    @FXML
    private Button btnProductos;

    @FXML
    private Button btnPedidos;

    @FXML
    private Button btnRutas;

    @FXML
    private Button btnCerrarSesion;

    // 🔹 Inicialización
    @FXML
    private void initialize() {
        // Aquí podrías cargar estadísticas desde la base de datos
        lblPedidosHoy.setText("15");
        lblVentasDia.setText("$1.240.000");
        lblRepartidoresActivos.setText("4");
        lblProductoMasVendido.setText("Pechuga de Pollo");
    }

    // 🔹 Método para establecer el nombre del administrador
    public void setNombreAdmin(String nombre) {
        lblNombreAdmin.setText("Bienvenido, " + nombre);
    }

    // 🔹 Botón "Inicio"
    @FXML
    private void mostrarInicio(ActionEvent event) {
        mostrarAlerta("Inicio", "Sección de inicio del administrador.");
    }

    // 🔹 Botón "Usuarios"
    @FXML
    private void abrirUsuarios(ActionEvent event) {
        abrirVentana("/vista/VistaUsuarios.fxml", event);
    }

    // 🔹 Botón "Productos"
    @FXML
    private void abrirProductos(ActionEvent event) {
        abrirVentana("/vista/VistaProductos.fxml", event);
    }

    // 🔹 Botón "Pedidos"
    @FXML
    private void abrirPedidos(ActionEvent event) {
        abrirVentana("/vista/VistaPedidos.fxml", event);
    }

    // 🔹 Botón "Rutas"
    @FXML
    private void abrirRutas(ActionEvent event) {
        abrirVentana("/vista/VistaRutas.fxml", event);
    }

    // 🔹 Botón "Cerrar Sesión"
    @FXML
    private void cerrarSesion(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/vista/VistaLogin.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.setTitle("Iniciar Sesión");
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            mostrarAlerta("Error", "No se pudo cerrar la sesión correctamente.");
        }
    }

    // 🔹 Método auxiliar para cambiar de ventana
    private void abrirVentana(String rutaFXML, ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(rutaFXML));
            Parent root = loader.load();
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            mostrarAlerta("Error", "No se pudo abrir la vista solicitada.");
        }
    }

    // 🔹 Método auxiliar para mostrar alertas
    private void mostrarAlerta(String titulo, String mensaje) {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }
}
