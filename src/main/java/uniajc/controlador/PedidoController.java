package uniajc.controlador;

// Importaciones necesarias
import uniajc.dao.*;
import uniajc.modelo.*;
import javax.swing.*;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.sql.Connection;
import java.util.List;


public class PedidoController {
    private PedidoDAO dao;

    // Constructor del controlador que recibe la conexión a la base de datos
    public PedidoController(Connection conexion) {
        this.dao = new PedidoDAO(conexion);
    }

    // Métodos para gestionar pedidos
    // Crear
    public void registrarPedido(
        int id_Cliente,
        int id_Estado_Pedido,
        Timestamp Fecha_Pedido,
        double Total_Pedido,
        String Metodo_Pago,
        String Direccion_Entrega) {
        Pedido pedido = new Pedido(id_Cliente, id_Estado_Pedido, Fecha_Pedido, Total_Pedido, Metodo_Pago, Direccion_Entrega);
        try {
            dao.registrarPedido(pedido);
            JOptionPane.showMessageDialog(null, "Pedido registrado exitosamente.");
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al registrar el pedido: " + e.getMessage());
        }
    }

    // Actualizar
    public void actualizarPedido(Pedido pedido) {
        try {
            dao.actualizarPedido(pedido);
            JOptionPane.showMessageDialog(null, "Pedido actualizado exitosamente.");
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al actualizar el pedido: " + e.getMessage());
        }
    }

    // Eliminar
    public void eliminarPedido(int id) {
        try {
            dao.eliminarPedido(id);
            JOptionPane.showMessageDialog(null, "Pedido eliminado exitosamente.");
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al eliminar el pedido: " + e.getMessage());
        }
    }

    // Leer
    public List<Pedido> listarPedidos() {
        try {
            return dao.listarPedidos();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al listar pedidos: " + e.getMessage());
            return null;
        }
    }

    // Buscar
    public void BuscarPedidoPorId(int id) {
        try {
            dao.buscarPorId(id);
            JOptionPane.showMessageDialog(null, "Pedido encontrado exitosamente. ");
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null,"Error en la busqueda del pedido" + e.getMessage());
        }
    }
    
}