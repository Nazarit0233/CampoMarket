package uniajc.controlador;

// Importaciones necesarias
import uniajc.dao.*;
import uniajc.modelo.*;
import javax.swing.*;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

public class PedidoControlador {
    private PedidoDAO dao;

    // Constructor del controlador que recibe la conexión a la base de datos
    public PedidoControlador(Connection conexion) {
        this.dao = new PedidoDAO(conexion);
    }

    // Métodos para gestionar pedidos
    // Crear
    public void realizarPedido(CarritoControlador carrito, int idCliente, String metodoPago, String direccionEntrega) {
    try {
        double total = carrito.calcularTotal();
        if (total <= 0) {
            JOptionPane.showMessageDialog(null, "El carrito está vacío o el total no es válido.");
            return;
        }

        Pedido pedido = new Pedido(idCliente, 1, new Timestamp(System.currentTimeMillis()), total, metodoPago, direccionEntrega);

        // 1️⃣ Registrar pedido y obtener su ID
        int idPedido = dao.registrarPedidoYObtenerId(pedido);

        // 2️⃣ Registrar cada producto del carrito
        for (ItemCarrito item : carrito.obtenerItems()) {
            dao.registrarDetallePedido(idPedido, item);
        }

        JOptionPane.showMessageDialog(null, "Pedido #" + idPedido + " registrado correctamente.");
        carrito.vaciarCarrito();

    } catch (SQLException e) {
        JOptionPane.showMessageDialog(null, "Error al realizar el pedido: " + e.getMessage());
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

    // Mostrar
    public List<Pedido> listarPedidos() {
        try {
            return dao.listarPedidos() != null ? dao.listarPedidos() : new ArrayList<>();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al listar pedidos: " + e.getMessage());
            return null;
        }
    }

    // Buscar
    public Pedido BuscarPedidoPorId(int id) {
        try {
            Pedido pedido = dao.buscarPorId(id);
            if (pedido != null) {
                JOptionPane.showMessageDialog(null, "Pedido encontrado exitosamente. ");
            } else {
                JOptionPane.showMessageDialog(null, "No se encontró el pedido. ");
            }
            return pedido;
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error en la busqueda del pedido" + e.getMessage());
            return null;
        }
    }

}