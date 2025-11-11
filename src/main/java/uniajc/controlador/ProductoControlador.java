package uniajc.controlador;

// Importaciones necesarias
import uniajc.dao.*;
import uniajc.modelo.*;
import java.sql.*;
import java.util.List;
import javafx.scene.control.Alert;

public class ProductoControlador {
    private final ProductoDAO dao;

    // Constructor del controlador que recibe la conexión de la base de datos
    public ProductoControlador(Connection conexion) {
        this.dao = new ProductoDAO(conexion);
    }

    // Metodos para gestionar productos
    // Crear
    public void registrarProducto(int id_stock, String nombre, double precio, int cantidad_disponible) {
        if (nombre == null || nombre.trim().isEmpty()) {
            mostrarAlerta("Advertencia", "El nombre del producto no puede estar vacío.", Alert.AlertType.WARNING);
            return;
        }

        try {
            dao.registrarProducto(new Producto(id_stock, nombre, precio, cantidad_disponible));
            mostrarAlerta("Éxito", "Producto registrado correctamente.", Alert.AlertType.INFORMATION);
        } catch (SQLException e) {
            mostrarAlerta("Error", "Error al registrar: " + e.getMessage(), Alert.AlertType.ERROR);
        }
    }

    // Actualizar
    public void actualizarProducto(int id_producto, int id_stock, String nombre, double precio, int cantidad_disponible) {
        try {
            dao.actualizarProducto(new Producto(id_producto, id_stock, nombre, precio, cantidad_disponible));
            mostrarAlerta("Éxito", "Producto actualizado correctamente.", Alert.AlertType.INFORMATION);
        } catch (SQLException e) {
            mostrarAlerta("Error", "Error al actualizar: " + e.getMessage(), Alert.AlertType.ERROR);
        }
    }

    // Eliminar
    public void eliminarProducto(int id) {
        try {
            dao.eliminarProducto(id);
            mostrarAlerta("Éxito", "Producto eliminado correctamente.", Alert.AlertType.INFORMATION);
        } catch (SQLException e) {
            mostrarAlerta("Error", "Error al eliminar: " + e.getMessage(), Alert.AlertType.ERROR);
        }
    }

    // Mostrar
    public List<Producto> listarProductos() {
        try {
            return dao.listarProductos();
        } catch (SQLException e) {
            mostrarAlerta("Error", "Error al listar: " + e.getMessage(), Alert.AlertType.ERROR);
            return null;
        }
    }

    // Buscar
    public Producto buscarProductoPorId(int id) {
        try {
            Producto producto = dao.buscarPorId(id);
            if (producto != null) {
                mostrarAlerta("Éxito", "Producto encontrado exitosamente.", Alert.AlertType.INFORMATION);
            } else {
                mostrarAlerta("Aviso", "No se encontró el producto con ID " + id, Alert.AlertType.WARNING);
            }
            return producto;
        } catch (SQLException e) {
            mostrarAlerta("Error", "Error al buscar: " + e.getMessage(), Alert.AlertType.ERROR);
            return null;
        }
    }

    // Método auxiliar para mostrar alertas JavaFX
    private void mostrarAlerta(String titulo, String mensaje, Alert.AlertType tipo) {
        Alert alerta = new Alert(tipo);
        alerta.setTitle(titulo);
        alerta.setHeaderText(null);
        alerta.setContentText(mensaje);
        alerta.showAndWait();
    }
}
