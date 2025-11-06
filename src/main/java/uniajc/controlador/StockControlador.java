package uniajc.controlador;

import uniajc.dao.*;
import uniajc.modelo.*;
import java.sql.*;
import java.util.List;
import javax.swing.JOptionPane;

public class StockControlador {
    private StockDAO dao;

    // Constructor del controlador que recibe la conexión de la base de datos
    public StockControlador(Connection conexion) {
        this.dao = new StockDAO(conexion);
    }

    // Metodos para gestionar productos
    // Crear
    public void registrarStock(int id_Producto, int id_Almacenamiento, int cantidad_disponible) {
        try {
            dao.registrarStock(new Stock(id_Producto, id_Almacenamiento, cantidad_disponible));
            JOptionPane.showMessageDialog(null, " Stock registrado correctamente.");
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, " Error al registrar: " + e.getMessage());
        }
    }

    // Actualizar
    public void actualizarStock(int id_Producto, int id_Almacenamiento, int cantidad_disponible) {
        try {
            dao.actualizarStock(new Stock(id_Producto, id_Almacenamiento, cantidad_disponible));
            JOptionPane.showMessageDialog(null, "Stock actualizado correctamente.");
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, " Error al actualizar: " + e.getMessage());
        }
    }

    // Eliminar
    public void eliminarStock(int id_Stock) {
        try {
            dao.eliminarStock(id_Stock);
            JOptionPane.showMessageDialog(null,"Stock eliminado correctamente." );
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, " Error al eliminar: " + e.getMessage());
        }
    }

    // Mostrar
    public List<Stock> listarStock() {
        try {
            return dao.listarStock();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, " Error al listar: " + e.getMessage());
            return null;
        }
    }

    // Buscar
    public void buscarPorId (int id) {
        try {
            dao.buscarPorId(id);
            JOptionPane.showMessageDialog(null, " Stock encontrado exitozamente.");
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null," Error al buscar: " + e.getMessage());
        }
    }
}
