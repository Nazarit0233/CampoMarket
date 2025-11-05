package uniajc.controlador;

import uniajc.dao.*;
import uniajc.modelo.*;
import java.sql.*;
import java.util.List;
import javax.swing.JOptionPane;

public class ProductoControlador {
    private ProductoDAO dao;

    // Constructor del controlador que recibe la conexión de la base de datos
    public ProductoControlador(Connection conexion) {
        this.dao = new ProductoDAO(conexion);
    }

    // Metodos para gestionar productos
    // Crear
    public void registrarProducto(int id_stock, String nombre, double precio, int cantidad_disponible ) {
        try {
            dao.registrarProducto(new Producto(id_stock, nombre, precio, cantidad_disponible));
            JOptionPane.showMessageDialog(null, " Producto registrado correctamente.");            
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, " Error al registrar: " + e.getMessage());
        }
    }

    // Actualizar
    public void actualizarProducto(int id_stock, String nombre, double precio, int cantidad_disponible) {
        try {
            dao.actualizarProducto(new Producto(id_stock, nombre, precio, cantidad_disponible));
            JOptionPane.showInternalMessageDialog(null, " Producto actualizado correctamente.");
        } catch (SQLException e) {
            JOptionPane.showInternalMessageDialog(null, " Error al actualizar: " + e.getMessage());
        }
    }

    // Eliminar
    public void eliminarProducto(int id) {
        try {
            dao.eliminarProducto(id);
            JOptionPane.showMessageDialog(null, " Producto eliminado.");
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, " Error al eliminar: " + e.getMessage());
        }
    }

    // Mostrar
    public List<Producto> listarProductos() {
        try {
            return dao.listarProductos();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, " Error al listar productos: " + e.getMessage());
            return null;
        }
    }

    // Buscar
    public void BuscarProductoPorId(int id) {
        try {
            dao.buscarPorId(id);
            JOptionPane.showMessageDialog(null, " Producto encontrado exitozamente.");
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null," Error al buscar el producto." + e.getMessage());
        }
    }
}
