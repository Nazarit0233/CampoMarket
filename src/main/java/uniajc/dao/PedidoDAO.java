package uniajc.dao;

import uniajc.db.ConexionDatabase;
import uniajc.modelo.Pedido;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

public class PedidoDAO {

    // Conexión a la base de datos
    private Connection conexion;

    // Constructor que recibe la conexión
    public PedidoDAO(Connection conexion) {
        this.conexion = conexion;
    }

    // Aquí irían los métodos CRUD para la entidad Pedido
    // Crear con procediminetos almacenados
    public boolean registrarPedido(Pedido pedido) throws SQLException {
        boolean registro = false;
        String sql = "{ CALL RegistrarPedido(?, ?, ?, ?, ?) }";

        try (Connection con = ConexionDatabase.getConnection();
                CallableStatement cs = con.prepareCall(sql)) {

            cs.setInt(1, pedido.getid_Cliente());
            cs.setInt(2, pedido.getid_Estado_Pedido());
            cs.setDouble(3, pedido.getTotalPedido());
            cs.setString(4, pedido.getMetodoPago());
            cs.setString(5, pedido.getDireccionEntrega());

            cs.execute();
            registro = true;

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al registrar pedido: " + e.getMessage());
        }

        return registro;
    }

    // Mostrar con procedimientos almacenados
    public List<Pedido> listarPedidos() throws SQLException {
        List<Pedido> lista = new ArrayList<>();
        String sql = "{ CALL ListarPedidos() }";

        try (Connection con = ConexionDatabase.getConnection();
                CallableStatement cs = con.prepareCall(sql);
                ResultSet rs = cs.executeQuery()) {

            while (rs.next()) {
                Pedido p = new Pedido();
                p.setid_Pedido(rs.getInt("id_Pedido"));
                p.setid_Cliente(rs.getInt("id_Cliente"));
                p.setid_Estado_Pedido(rs.getInt("id_Estado_Pedido"));
                p.setFechaPedido(rs.getTimestamp("Fecha_Pedido"));
                p.setTotalPedido(rs.getDouble("Total_Pedido"));
                p.setMetodoPago(rs.getString("Metodo_Pago"));
                p.setDireccionEntrega(rs.getString("Direccion_Entrega"));
                lista.add(p);
            }

        } catch (SQLException e) {
            System.err.println("Error al listar pedidos: " + e.getMessage());
        }

        return lista;
    }

    // Eliminar con procedimientos almacenandos
    public boolean eliminarPedido(int idPedido) throws SQLException {
        boolean eliminado = false;
        String sql = "{ CALL EliminarPedido(?) }";

        try (Connection con = ConexionDatabase.getConnection();
                CallableStatement cs = con.prepareCall(sql)) {

            cs.setInt(1, idPedido);
            cs.execute();
            eliminado = true;

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al eliminar pedido: " + e.getMessage());
        }

        return eliminado;
    }

    // Actualizar con procedimientos almacenados
    public boolean actualizarPedido(Pedido pedido) throws SQLException {
        boolean actualizado = false;
        String sql = "{ CALL ActualizarPedido(?, ?, ?, ?, ?, ?) }";

        try (Connection con = ConexionDatabase.getConnection();
                CallableStatement cs = con.prepareCall(sql)) {

            cs.setInt(1, pedido.getid_Pedido());
            cs.setInt(2, pedido.getid_Cliente());
            cs.setInt(3, pedido.getid_Estado_Pedido());
            cs.setDouble(4, pedido.getTotalPedido());
            cs.setString(5, pedido.getMetodoPago());
            cs.setString(6, pedido.getDireccionEntrega());

            cs.execute();
            actualizado = true;

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al actualizar pedido: " + e.getMessage());
        }

        return actualizado;
    }

    // Buscar Pedido por su ID
    public Pedido buscarPorId(int idPedido) throws SQLException {
        Pedido p = null;
        String sql = "{ CALL BuscarPedidoPorId(?) }";

        try (Connection con = ConexionDatabase.getConnection();
                CallableStatement cs = con.prepareCall(sql)) {

            cs.setInt(1, idPedido);
            try (ResultSet rs = cs.executeQuery()) {
                if (rs.next()) {
                    p = new Pedido();
                    p.setid_Pedido(rs.getInt("id_Pedido"));
                    p.setid_Cliente(rs.getInt("id_Cliente"));
                    p.setid_Estado_Pedido(rs.getInt("id_Estado_Pedido"));
                    p.setFechaPedido(rs.getTimestamp("Fecha_Pedido"));
                    p.setTotalPedido(rs.getDouble("Total_Pedido"));
                    p.setMetodoPago(rs.getString("Metodo_Pago"));
                    p.setDireccionEntrega(rs.getString("Direccion_Entrega"));
                }
            }

        } catch (SQLException e) {
            System.err.println("Error al buscar pedido: " + e.getMessage());
        }

        return p;
    }

}
